/**
 * Profile JavaScript - Handles profile editing functionality
 */

// Get user ID from cookie
function getUserIdFromCookie() {
    const cookies = document.cookie.split(';');
    for (let cookie of cookies) {
        const [name, value] = cookie.trim().split('=');
        if (name === 'user_id') {
            return parseInt(value);
        }
    }
    return null;
}

// Update profile function
function updateProfile() {
    const userId = getUserIdFromCookie();

    if (!userId) {
        alert('Không thể xác định người dùng. Vui lòng đăng nhập lại.');
        return;
    }

    // Validate form
    if (!validateForm()) {
        return;
    }

    // Collect form data
    const profileData = {
        hoTen: document.getElementById('editHoTen').value.trim(),
        email: document.getElementById('editEmail').value.trim(),
        soDienThoai: document.getElementById('editSoDienThoai').value.trim(),
        CCCD: document.getElementById('editCCCD').value.trim(),
        diaChi: document.getElementById('editDiaChi').value.trim()
    };

    // Show loading state
    const saveButton = document.querySelector('#editProfileModal .btn-success');
    const originalText = saveButton.innerHTML;
    saveButton.innerHTML = '<i class="bi bi-hourglass-split me-1"></i>Đang lưu...';
    saveButton.disabled = true;

    // Send PUT request
    fetch(`/user/profile/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(profileData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text().then(text => {
            try {
                return text ? JSON.parse(text) : {};
            } catch (e) {
                return text;
            }
        });
    })
    .then(data => {
        // Success
        alert('Cập nhật thông tin thành công!');

        // Close modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('editProfileModal'));
        modal.hide();

        // Reload page to show updated data
        window.location.reload();
    })
    .catch(error => {
        console.error('Error updating profile:', error);
        alert('Không thể cập nhật thông tin. Vui lòng thử lại sau.');
    })
    .finally(() => {
        // Reset button state
        saveButton.innerHTML = originalText;
        saveButton.disabled = false;
    });
}

// Form validation
function validateForm() {
    const hoTen = document.getElementById('editHoTen').value.trim();
    const email = document.getElementById('editEmail').value.trim();
    const soDienThoai = document.getElementById('editSoDienThoai').value.trim();
    const cccd = document.getElementById('editCCCD').value.trim();

    // Validate required fields
    if (!hoTen) {
        alert('Vui lòng nhập họ và tên.');
        document.getElementById('editHoTen').focus();
        return false;
    }

    if (!email) {
        alert('Vui lòng nhập email.');
        document.getElementById('editEmail').focus();
        return false;
    }

    if (!soDienThoai) {
        alert('Vui lòng nhập số điện thoại.');
        document.getElementById('editSoDienThoai').focus();
        return false;
    }

    if (!cccd) {
        alert('Vui lòng nhập CCCD.');
        document.getElementById('editCCCD').focus();
        return false;
    }

    // Validate email format
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert('Email không hợp lệ.');
        document.getElementById('editEmail').focus();
        return false;
    }

    // Validate phone number (Vietnamese format)
    const phoneRegex = /^(0[3|5|7|8|9])+([0-9]{8})$/;
    if (!phoneRegex.test(soDienThoai)) {
        alert('Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại Việt Nam (10 số, bắt đầu bằng 03, 05, 07, 08, 09).');
        document.getElementById('editSoDienThoai').focus();
        return false;
    }

    // Validate CCCD (12 digits)
    const cccdRegex = /^[0-9]{12}$/;
    if (!cccdRegex.test(cccd)) {
        alert('CCCD không hợp lệ. Vui lòng nhập 12 chữ số.');
        document.getElementById('editCCCD').focus();
        return false;
    }

    return true;
}

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    console.log('Profile.js loaded');

    // Debug: Check if user is logged in
    const userId = getUserIdFromCookie();
    console.log('User ID from cookie:', userId);

    // Add form submit handler
    const editForm = document.getElementById('editProfileForm');
    if (editForm) {
        editForm.addEventListener('submit', function(e) {
            e.preventDefault();
            updateProfile();
        });
    }

    // Add input validation on blur
    const emailInput = document.getElementById('editEmail');
    if (emailInput) {
        emailInput.addEventListener('blur', function() {
            const email = this.value.trim();
            if (email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
                this.classList.add('is-invalid');
            } else {
                this.classList.remove('is-invalid');
            }
        });
    }

    const phoneInput = document.getElementById('editSoDienThoai');
    if (phoneInput) {
        phoneInput.addEventListener('blur', function() {
            const phone = this.value.trim();
            if (phone && !/^(0[3|5|7|8|9])+([0-9]{8})$/.test(phone)) {
                this.classList.add('is-invalid');
            } else {
                this.classList.remove('is-invalid');
            }
        });
    }

    const cccdInput = document.getElementById('editCCCD');
    if (cccdInput) {
        cccdInput.addEventListener('blur', function() {
            const cccd = this.value.trim();
            if (cccd && !/^[0-9]{12}$/.test(cccd)) {
                this.classList.add('is-invalid');
            } else {
                this.classList.remove('is-invalid');
            }
        });
    }

    // Debug modal events
    const modal = document.getElementById('editProfileModal');
    if (modal) {
        modal.addEventListener('show.bs.modal', function() {
            console.log('Modal is opening');
        });

        modal.addEventListener('shown.bs.modal', function() {
            console.log('Modal is fully shown');
        });
    }
});
