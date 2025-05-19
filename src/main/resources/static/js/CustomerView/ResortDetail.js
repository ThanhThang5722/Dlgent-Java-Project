// Hàm để lấy giá trị cookie theo tên
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return null;
}

// Hàm để lấy userId từ cookie
function getUserIdFromCookie() {
    return getCookie('user_id');
}

$(document).ready(function () {
    // Date Range Picker
    $('#dateRange').daterangepicker({
        opens: 'left',
        autoApply: true,
        minDate: moment(),
        locale: {
            format: 'DD/MM/YYYY',
            separator: ' - ',
            applyLabel: 'Apply',
            cancelLabel: 'Cancel',
            fromLabel: 'From',
            toLabel: 'To',
            customRangeLabel: 'Custom',
            daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
            monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            firstDay: 1
        }
    }, function (start, end, label) {
        $('#checkIn').val(start.format('YYYY-MM-DD'));
        $('#checkOut').val(end.format('YYYY-MM-DD'));
    });

    // Handle star rating input
    $('input[name="diem"]').change(function () {
        var ratingValue = $(this).val();
        var ratingText = '';

        switch (parseInt(ratingValue)) {
            case 5:
                ratingText = 'Xuất sắc';
                break;
            case 4:
                ratingText = 'Rất tốt';
                break;
            case 3:
                ratingText = 'Tốt';
                break;
            case 2:
                ratingText = 'Bình thường';
                break;
            case 1:
                ratingText = 'Không hài lòng';
                break;
            default:
                ratingText = 'Chọn đánh giá';
        }

        $('.rating-text-select').text(ratingText);
    });

    // Tự động điền userId từ cookie vào input field
    const userId = getUserIdFromCookie();
    if (userId) {
        $('#khachHangId').val(userId);
    }

    // Cập nhật số lượng mục trong giỏ hàng
    updateCartCount();
});

function showAlert() {
    alert("Gửi đánh giá thành công!");
    return true;
}

// Thêm event listener cho tất cả các nút "Thêm vào giỏ"
$(document).ready(function () {
    // Gắn sự kiện click cho tất cả các nút có class add-to-cart-btn
    $(document).on('click', '.add-to-cart-btn', function (event) {
        event.preventDefault();

        // Lấy dữ liệu từ thuộc tính data-*
        const button = $(this);
        const resortId = button.data('resort-id');
        const roomTypeId = button.data('room-type-id');
        let packageId = button.data('package-id');
        const checkIn = button.data('check-in');
        const checkOut = button.data('check-out');
        const soNguoi = button.data('so-nguoi');

        // Nếu packageId là "null" (string), chuyển thành null (object)
        if (packageId === "null") {
            packageId = null;
        }

        // Tạo đối tượng dữ liệu để gửi đi
        const data = {
            // Không cần chỉ định khachHangId, server sẽ lấy từ cookie
            goiDatPhongId: packageId || roomTypeId, // Nếu không có packageId thì dùng roomTypeId
            ngayBatDau: checkIn,
            ngayKetThuc: checkOut
        };

        // Gọi API để thêm vào giỏ hàng
        fetch('/api/cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data),
            redirect: 'follow'
        })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url;
                    return;
                }

                if (response.ok) {
                    // Hiển thị thông báo thành công
                    alert('Đã thêm vào giỏ hàng thành công!');
                    // Cập nhật số lượng mục trong giỏ hàng
                    // updateCartCount();
                } else {
                    // Hiển thị thông báo lỗi
                    alert('Có lỗi xảy ra khi thêm vào giỏ hàng!');
                }
            })
            .catch(error => {
                console.error('Lỗi:', error);
                alert('Có lỗi xảy ra khi thêm vào giỏ hàng!');
            });
    });
});

// // Hàm cập nhật số lượng mục trong giỏ hàng
// function updateCartCount() {
//     fetch('/gio-hang/count')
//         .then(response => response.json())
//         .then(data => {
//             // Cập nhật số lượng mục trong giỏ hàng
//             const cartCountElement = document.querySelector('.cart-count');
//             if (cartCountElement) {
//                 cartCountElement.textContent = data.count;
//
//                 // Ẩn badge nếu không có mục nào trong giỏ hàng
//                 if (data.count === 0) {
//                     cartCountElement.style.display = 'none';
//                 } else {
//                     cartCountElement.style.display = 'inline-block';
//                 }
//             }
//             console.log('Số lượng mục trong giỏ hàng:', data.count);
//         })
//         .catch(error => {
//             console.error('Lỗi khi lấy số lượng mục trong giỏ hàng:', error);
//         });
// }