document.addEventListener('DOMContentLoaded', function () {
    const cancelBtn = document.getElementById('cancelBookingBtn');

    if (cancelBtn) {
        cancelBtn.addEventListener('click', function () {
            if (confirm('Bạn có chắc chắn muốn hủy đặt phòng này không? Việc hủy có thể sẽ không được hoàn tiền.')) {
                // Lấy ID đặt phòng từ URL
                const urlParams = new URLSearchParams(window.location.search);
                const bookingId = urlParams.get('id');

                if (bookingId) {
                    // Gọi API hủy phòng
                    fetch(`/api/booking/${bookingId}`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    })
                        .then(response => {
                            if (response.ok) {
                                alert('Đã hủy đặt phòng thành công!');
                                window.location.href = '/user/booking-history';
                            } else {
                                return response.json().then(data => {
                                    throw new Error(data || 'Có lỗi xảy ra khi hủy đặt phòng');
                                });
                            }
                        })
                        .catch(error => {
                            alert('Lỗi: ' + error.message);
                        });
                }
            }
        });
    }
});
