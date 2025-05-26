// Global variables
let currentUserType = 'customer'; // 'customer' or 'partner'
let currentMode = 'signin'; // 'signin' or 'signup'

// DOM elements
const authModal = new bootstrap.Modal(document.getElementById('authModal'));
const modalTitle = document.getElementById('authModalLabel');
const signinForm = document.getElementById('signinForm');
const signupForm = document.getElementById('signupForm');
const switchText = document.getElementById('switchText');
const switchBtn = document.getElementById('switchBtn');
const alertMessage = document.getElementById('alertMessage');

// Initialize event listeners
document.addEventListener('DOMContentLoaded', function() {
    // Form submission handlers
    signinForm.addEventListener('submit', handleSignin);
    signupForm.addEventListener('submit', handleSignup);

    // Switch between signin and signup
    switchBtn.addEventListener('click', toggleAuthMode);
});

/**
 * Open authentication modal
 * @param {string} userType - 'customer' or 'partner'
 * @param {string} mode - 'signin' or 'signup'
 */
function openModal(userType, mode) {
    currentUserType = userType;
    currentMode = mode;

    updateModalContent();
    clearAlert();
    clearForms();
    authModal.show();
}

/**
 * Update modal content based on current user type and mode
 */
function updateModalContent() {
    const userTypeText = currentUserType === 'customer' ? 'Khách Hàng' : 'Đối Tác';
    const modeText = currentMode === 'signin' ? 'Đăng Nhập' : 'Đăng Ký';

    modalTitle.textContent = `${modeText} ${userTypeText}`;

    if (currentMode === 'signin') {
        signinForm.classList.remove('d-none');
        signupForm.classList.add('d-none');
        switchText.textContent = 'Chưa có tài khoản?';
        switchBtn.textContent = 'Đăng ký ngay';
    } else {
        signinForm.classList.add('d-none');
        signupForm.classList.remove('d-none');
        switchText.textContent = 'Đã có tài khoản?';
        switchBtn.textContent = 'Đăng nhập ngay';

        // Show/hide partner-specific fields
        const partnerFields = document.getElementById('partnerFields');
        if (currentUserType === 'partner') {
            partnerFields.classList.remove('d-none');
            // Make partner fields required
            partnerFields.querySelectorAll('input').forEach(input => {
                input.setAttribute('required', 'required');
            });
        } else {
            partnerFields.classList.add('d-none');
            // Remove required attribute from partner fields
            partnerFields.querySelectorAll('input').forEach(input => {
                input.removeAttribute('required');
            });
        }
    }
}

/**
 * Toggle between signin and signup modes
 */
function toggleAuthMode() {
    currentMode = currentMode === 'signin' ? 'signup' : 'signin';
    updateModalContent();
    clearAlert();
    clearForms();
}

/**
 * Handle signin form submission
 */
async function handleSignin(event) {
    event.preventDefault();

    const username = document.getElementById('signin-username').value;
    const password = document.getElementById('signin-password').value;

    if (!username || !password) {
        showAlert('Vui lòng nhập đầy đủ thông tin', 'danger');
        return;
    }

    try {
        showLoading(true);

        const endpoint = currentUserType === 'customer' ? '/api/signin' : '/api/partner-signin';

        const response = await fetch(endpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        const data = await response.json();

        if (response.ok) {
            showAlert('Đăng nhập thành công!', 'success');

            // Store token if provided
            if (data.token) {
                document.cookie = `token=${data.token}; path=/; HttpOnly`;
            }

            // Redirect based on user type
            setTimeout(() => {
                if (currentUserType === 'customer') {
                    window.location.href = '/homepage';
                } else {
                    window.location.href = '/admin/partner-account'; // Redirect to partner management page
                }
            }, 1500);

        } else {
            showAlert(data.message || 'Đăng nhập thất bại', 'danger');
        }

    } catch (error) {
        console.error('Login error:', error);
        showAlert('Không thể kết nối đến máy chủ', 'danger');
    } finally {
        showLoading(false);
    }
}

/**
 * Handle signup form submission
 */
async function handleSignup(event) {
    event.preventDefault();

    const formData = new FormData(signupForm);
    const data = Object.fromEntries(formData.entries());

    // Validate required fields
    let requiredFields = ['fullName', 'email', 'phoneNumber', 'cccd', 'address', 'username', 'password'];

    // Add partner-specific required fields
    if (currentUserType === 'partner') {
        requiredFields = requiredFields.concat(['bankName', 'bankAccountNumber', 'bankAccountName']);
    }

    for (const field of requiredFields) {
        if (!data[field]) {
            showAlert('Vui lòng nhập đầy đủ thông tin bắt buộc', 'danger');
            return;
        }
    }

    // Validate email format
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(data.email)) {
        showAlert('Email không hợp lệ', 'danger');
        return;
    }

    // Validate phone number format
    const phoneRegex = /^[0-9]{10,11}$/;
    if (!phoneRegex.test(data.phoneNumber)) {
        showAlert('Số điện thoại không hợp lệ', 'danger');
        return;
    }

    try {
        showLoading(true);

        const endpoint = currentUserType === 'customer' ? '/api/account/customer' : '/api/account/partner';

        const response = await fetch(endpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            showAlert('Đăng ký thành công! Vui lòng kiểm tra email để xác nhận tài khoản.', 'success');

            // Switch to signin mode after successful signup
            setTimeout(() => {
                currentMode = 'signin';
                updateModalContent();
                clearForms();
            }, 2000);

        } else {
            const errorText = await response.text();
            showAlert(errorText || 'Đăng ký thất bại', 'danger');
        }

    } catch (error) {
        console.error('Signup error:', error);
        showAlert('Không thể kết nối đến máy chủ', 'danger');
    } finally {
        showLoading(false);
    }
}

/**
 * Show alert message
 * @param {string} message - Alert message
 * @param {string} type - Alert type ('success' or 'danger')
 */
function showAlert(message, type) {
    alertMessage.className = `alert alert-${type}`;
    alertMessage.textContent = message;
    alertMessage.classList.remove('d-none');

    // Auto hide success messages
    if (type === 'success') {
        setTimeout(() => {
            clearAlert();
        }, 5000);
    }
}

/**
 * Clear alert message
 */
function clearAlert() {
    alertMessage.classList.add('d-none');
    alertMessage.className = 'alert d-none';
    alertMessage.textContent = '';
}

/**
 * Clear all form inputs
 */
function clearForms() {
    signinForm.reset();
    signupForm.reset();
}

/**
 * Show/hide loading state
 * @param {boolean} loading - Loading state
 */
function showLoading(loading) {
    const submitBtns = document.querySelectorAll('.auth-form button[type="submit"]');

    submitBtns.forEach(btn => {
        if (loading) {
            btn.disabled = true;
            btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang xử lý...';
        } else {
            btn.disabled = false;
            if (btn.closest('#signinForm')) {
                btn.innerHTML = '<i class="fas fa-sign-in-alt"></i> Đăng Nhập';
            } else {
                btn.innerHTML = '<i class="fas fa-user-plus"></i> Đăng Ký';
            }
        }
    });
}

/**
 * Handle modal close event
 */
document.getElementById('authModal').addEventListener('hidden.bs.modal', function() {
    clearAlert();
    clearForms();
    showLoading(false);
});

// Add smooth scrolling for better UX
document.documentElement.style.scrollBehavior = 'smooth';

// Add keyboard navigation
document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape' && authModal._isShown) {
        authModal.hide();
    }
});

// Add form validation styling
document.addEventListener('input', function(event) {
    if (event.target.classList.contains('form-control')) {
        if (event.target.checkValidity()) {
            event.target.style.borderColor = '#28a745';
        } else {
            event.target.style.borderColor = '#dc3545';
        }
    }
});

// Reset form validation styling on focus
document.addEventListener('focus', function(event) {
    if (event.target.classList.contains('form-control')) {
        event.target.style.borderColor = '#28a745';
    }
}, true);
