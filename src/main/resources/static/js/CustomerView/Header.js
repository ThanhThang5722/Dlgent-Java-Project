/**
 * Header JavaScript - Common functionality for all pages
 * Handles logout and other header-related functions
 */

// Logout function
function logout() {
    if (confirm('Bạn có chắc chắn muốn đăng xuất?')) {
        fetch('/api/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data) {
                alert('Đăng xuất thành công!');
                // Redirect to homepage after logout
                window.location.href = '/';
            }
        })
        .catch(error => {
            console.error('Lỗi khi đăng xuất:', error);
            alert('Có lỗi xảy ra khi đăng xuất!');
        });
    }
}

// Update cart count function (can be called from other pages)
function updateCartCount() {
    // Only update if cart count element exists
    const cartCountElement = document.querySelector('.cart-count');
    if (cartCountElement) {
        fetch('/api/cart/count')
            .then(response => response.json())
            .then(data => {
                cartCountElement.textContent = data.count || 0;
                
                // Hide badge if count is 0
                if (data.count === 0) {
                    cartCountElement.style.display = 'none';
                } else {
                    cartCountElement.style.display = 'flex';
                }
            })
            .catch(error => {
                console.error('Lỗi khi cập nhật số lượng giỏ hàng:', error);
            });
    }
}

// Initialize header functionality when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    // Update cart count on page load if user is logged in
    const cartCountElement = document.querySelector('.cart-count');
    if (cartCountElement) {
        updateCartCount();
    }
    
    // Add event listeners for dropdown animations
    const avatarButton = document.querySelector('.avatar-button');
    if (avatarButton) {
        avatarButton.addEventListener('click', function() {
            // Add any additional dropdown animations here if needed
        });
    }
});
