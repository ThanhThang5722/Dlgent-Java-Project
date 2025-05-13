function deleteCartItem(id) {
    if (confirm('Bạn có chắc chắn muốn xóa mục này khỏi giỏ hàng?')) {
        fetch(`/api/cart?id=${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    // Reload trang sau khi xóa thành công
                    window.location.reload();
                } else {
                    alert('Có lỗi xảy ra khi xóa mục khỏi giỏ hàng');
                }
            })
            .catch(error => {
                console.error('Lỗi:', error);
                alert('Có lỗi xảy ra khi xóa mục khỏi giỏ hàng');
            });
    }
}

// Cập nhật số lượng mục trong giỏ hàng
function updateCartCount() {
    fetch('/gio-hang/count')
        .then(response => response.json())
        .then(data => {
            // Cập nhật số lượng mục trong giỏ hàng
            const cartCountElement = document.querySelector('.cart-count');
            if (cartCountElement) {
                cartCountElement.textContent = data.count;

                // Ẩn badge nếu không có mục nào trong giỏ hàng
                if (data.count === 0) {
                    cartCountElement.style.display = 'none';
                } else {
                    cartCountElement.style.display = 'inline-block';
                }
            }
        })
        .catch(error => {
            console.error('Lỗi khi lấy số lượng mục trong giỏ hàng:', error);
        });
}

// Gọi hàm cập nhật khi trang được tải
$(document).ready(function () {
    updateCartCount();
});