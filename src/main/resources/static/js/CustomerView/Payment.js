// Hàm xử lý thanh toán qua ZaloPay
function processZaloPayPayment() {
    // Lấy tổng số tiền từ trang
    const totalPriceElement = document.querySelector('.text-danger.fs-5 span');
    if (!totalPriceElement) {
        alert('Không thể xác định số tiền thanh toán');
        return;
    }

    // Lấy giá trị số tiền và loại bỏ dấu phẩy
    const totalPriceText = totalPriceElement.textContent;
    // const amount = totalPriceText.replace(/,/g, '');
    const amount = 100000;

    // Lấy danh sách các ID đặt phòng từ các mục trong giỏ hàng
    const cartItems = document.querySelectorAll('.cart-item');
    if (!cartItems || cartItems.length === 0) {
        alert('Giỏ hàng của bạn đang trống');
        return;
    }
//TODO: sửa lại ID đặt phòng đang bị sai
    // Lấy ID đặt phòng đầu tiên (hoặc có thể xử lý nhiều ID nếu cần)
    const firstCartItem = cartItems[0];
    const idDatPhong = firstCartItem.getAttribute('data-id');

    if (!idDatPhong) {
        alert('Không thể xác định thông tin đặt phòng');
        return;
    }

    // Hiển thị thông báo đang xử lý
    const paymentButton = document.getElementById('paymentButton');
    const originalButtonText = paymentButton.textContent;
    paymentButton.textContent = 'Đang xử lý...';
    paymentButton.disabled = true;

    // Gọi API để tạo đơn hàng thanh toán
    fetch(`/api/zalopay/${idDatPhong}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ amount: amount })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Có lỗi xảy ra khi tạo đơn hàng thanh toán');
        }
        return response.json();
    })
    .then(data => {
        console.log('ZaloPay response:', data);
        
        // Kiểm tra nếu có lỗi từ ZaloPay
        if (data.error) {
            alert('Lỗi từ cổng thanh toán: ' + data.error);
            paymentButton.textContent = originalButtonText;
            paymentButton.disabled = false;
            return;
        }
        
        // Kiểm tra nếu có URL thanh toán
        if (data.order_url) {
            // Chuyển hướng đến trang thanh toán ZaloPay
            window.location.href = data.order_url;
        } else {
            alert('Không nhận được URL thanh toán từ cổng thanh toán');
            paymentButton.textContent = originalButtonText;
            paymentButton.disabled = false;
        }
    })
    .catch(error => {
        console.error('Lỗi:', error);
        alert('Có lỗi xảy ra: ' + error.message);
        paymentButton.textContent = originalButtonText;
        paymentButton.disabled = false;
    });
}

// Thêm sự kiện khi trang được tải
document.addEventListener('DOMContentLoaded', function() {
    // Tìm nút thanh toán và thêm sự kiện click
    const paymentButton = document.getElementById('paymentButton');
    if (paymentButton) {
        paymentButton.addEventListener('click', function(event) {
            event.preventDefault();
            processZaloPayPayment();
        });
    }
});
