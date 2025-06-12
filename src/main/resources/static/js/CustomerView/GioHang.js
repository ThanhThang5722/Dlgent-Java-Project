function deleteCartItem(id) {
    if (confirm('Bạn có chắc chắn muốn xóa mục này khỏi giỏ hàng?')) {
        fetch(`/api/cart?id=${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    // Xóa item khỏi DOM
                    const cartItem = document.querySelector(`[data-id="${id}"]`);
                    if (cartItem) {
                        cartItem.remove();
                    }

                    // Cập nhật tổng tiền
                    updateCartTotal();

                    // Cập nhật số lượng item trong giỏ hàng
                    updateCartCount();

                    // Kiểm tra nếu giỏ hàng trống thì hiển thị thông báo
                    const remainingItems = document.querySelectorAll('.cart-item');
                    if (remainingItems.length === 0) {
                        window.location.reload(); // Reload để hiển thị empty cart message
                    }
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

// Cập nhật số lượng phòng
function updateQuantity(cartItemId, change) {
    const quantityElement = document.getElementById('quantity-' + cartItemId);
    const currentQuantity = parseInt(quantityElement.textContent);
    const newQuantity = currentQuantity + change;

    // Kiểm tra số lượng tối thiểu
    if (newQuantity <= 0) {
        alert('Số lượng phòng phải lớn hơn 0');
        return;
    }

    // Gọi API để cập nhật số lượng
    fetch('/api/cart/update-quantity?id=' + cartItemId + '&quantity=' + newQuantity, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Cập nhật thất bại');
        }
        return response.json();
    })
    .then(data => {
        // Cập nhật số lượng hiển thị
        quantityElement.textContent = data.soLuongPhong;

        // Cập nhật giá tiền hiển thị (backend đã tính sẵn theo số ngày)
        const priceElement = document.getElementById('price-' + cartItemId);
        if (priceElement) {
            priceElement.textContent = new Intl.NumberFormat('vi-VN').format(data.tongGiaTien);
        }

        // Cập nhật tổng giá tiền của giỏ hàng
        updateCartTotal();

        console.log('Cập nhật số lượng thành công:', data);
        console.log('Giá mới (đã bao gồm số ngày):', data.tongGiaTien);
    })
    .catch(error => {
        console.error('Lỗi khi cập nhật số lượng:', error);
        alert('Có lỗi xảy ra khi cập nhật số lượng phòng');
    });
}

// Cập nhật tổng giá tiền giỏ hàng
function updateCartTotal() {
    let total = 0;

    // Tính tổng từ tất cả các item trong giỏ hàng
    document.querySelectorAll('[id^="price-"]').forEach(priceElement => {
        const priceText = priceElement.textContent.replace(/[,\.]/g, '');
        const price = parseInt(priceText) || 0;
        total += price;
    });

    // Cập nhật hiển thị tổng tiền trong cart summary
    const totalPriceDisplay = document.getElementById('total-price-display');
    const finalTotalDisplay = document.getElementById('final-total-display');

    if (totalPriceDisplay) {
        totalPriceDisplay.textContent = new Intl.NumberFormat('vi-VN').format(total);
    }
    if (finalTotalDisplay) {
        finalTotalDisplay.textContent = new Intl.NumberFormat('vi-VN').format(total);
    }

    // Cập nhật data attribute cho nút thanh toán
    const paymentButton = document.getElementById('paymentButton');
    if (paymentButton) {
        paymentButton.setAttribute('data-total-price', total);
    }
}

// Gọi hàm cập nhật khi trang được tải
$(document).ready(function () {
    updateCartCount();
});

document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM loaded');

    //  tất cả các nút
    const couponButtons = document.querySelectorAll('.apply-coupon-btn');
    console.log('Found coupon buttons:', couponButtons.length);

    couponButtons.forEach(button => {
        button.addEventListener('click', function() {
            const loaiPhongId = this.getAttribute('data-loai-phong-id');
            const chiTietId = this.getAttribute('data-chi-tiet-id');

            if (loaiPhongId) {
                fetchDiscountCoupons(loaiPhongId, chiTietId);
            } else {
                console.error('Missing loaiPhongId attribute');
            }

            document.getElementById('couponPopup').style.display = 'block';
            document.getElementById('modalBackdrop').style.display = 'block';
            document.body.classList.add('modal-open');
            document.body.style.overflow = 'hidden';
            document.body.style.paddingRight = '17px';
        });
    });

    document.getElementById('closeCouponBtn').addEventListener('click', closeModal);
    document.getElementById('closeCouponBtnFooter').addEventListener('click', closeModal);

    document.getElementById('couponPopup').addEventListener('click', function(e) {
        if (e.target === this) {
            closeModal();
        }
    });

    function closeModal() {
        document.getElementById('couponPopup').style.display = 'none';
        document.getElementById('modalBackdrop').style.display = 'none';
        document.body.classList.remove('modal-open');
        document.body.style.overflow = '';
        document.body.style.paddingRight = '';
    }
});

function getUserIdFromCookie() {

    const cookies = document.cookie.split(';');
    for (let cookie of cookies) {
        const [name, value] = cookie.trim().split('=');
        if (name === 'user_id') {
            return value;
        }
    }
    return null;
}

function fetchDiscountCoupons(loaiPhongId, chiTietId) {
    const khachHangId = getUserIdFromCookie();

    // loading
    const couponList = document.querySelector('.coupon-list');
    couponList.innerHTML = '<div class="text-center py-4"><i class="bi bi-hourglass-split me-2"></i>Đang tải mã giảm giá...</div>';

    fetch(`/api/kho-ma-giam-gia/${loaiPhongId}/${khachHangId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể lấy danh sách mã giảm giá');
            }
            return response.json();
        })
        .then(data => {
            // [active, expired]
            renderDiscountCoupons(data[0], data[1], loaiPhongId, chiTietId);
        })
        .catch(error => {
            console.error('Lỗi khi lấy mã giảm giá:', error);
            couponList.innerHTML = `<div class="text-center py-4 text-danger"><i class="bi bi-exclamation-circle me-2"></i>${error.message}</div>`;
        });
}

// danh sách mã giảm giá
function renderDiscountCoupons(activeCoupons, expiredCoupons, loaiPhongId, chiTietId) {
    const couponList = document.querySelector('.coupon-list');
    let html = '';

    // Render mã giảm giá còn hiệu lực
    if (activeCoupons && activeCoupons.length > 0) {
        html += `
        <div class="mb-4">
            <h3 class="h5 mb-3 d-flex align-items-center">
                <i class="bi bi-check-circle-fill text-success me-2"></i> Còn hiệu lực
            </h3>
            <div class="row g-3">
        `;

        activeCoupons.forEach(coupon => {
            html += createCouponCard(coupon, true, loaiPhongId);
        });

        html += `</div></div>`;
    }

    // Render mã giảm giá hết hạn
    if (expiredCoupons && expiredCoupons.length > 0) {
        html += `
        <div class="mb-4">
            <h3 class="h5 mb-3 d-flex align-items-center">
                <i class="bi bi-clock-fill text-danger me-2"></i> Đã hết hạn
            </h3>
            <div class="row g-3">
        `;

        expiredCoupons.forEach(coupon => {
            html += createCouponCard(coupon, false, loaiPhongId);
        });

        html += `</div></div>`;
    }

    // Nếu không có mã giảm giá nào
    if ((!activeCoupons || activeCoupons.length === 0) && (!expiredCoupons || expiredCoupons.length === 0)) {
        html = `<div class="text-center py-4 text-muted"><i class="bi bi-info-circle me-2"></i>Không có mã giảm giá nào khả dụng</div>`;
    }

    couponList.innerHTML = html;

    // Thêm event listener cho các nút áp dụng mã giảm giá
    document.querySelectorAll('.coupon-active button:not([disabled])').forEach(button => {
        button.addEventListener('click', function() {
            const couponCard = this.closest('.card');
            const couponId = couponCard.getAttribute('data-coupon-id');
            applyCoupon(couponId, chiTietId);
        });
    });
}

// HHTML cho một mã giảm giá
function createCouponCard(coupon, isActive, loaiPhongId) {
    const cardClass = isActive ? 'coupon-active' : 'coupon-expired';
    const statusClass = isActive ? 'text-success' : 'text-danger';
    const statusIcon = isActive ? 'bi-check-circle' : 'bi-x-circle';
    const statusText = isActive ? 'Sẵn sàng sử dụng' : 'Đã hết hạn';
    const buttonClass = isActive ? 'btn-primary' : 'btn-secondary disabled';
    const buttonDisabled = isActive ? '' : 'disabled';

    return `
    <div class="col-md-6">
        <div class="card ${cardClass} h-100" data-chi-tiet-id data-coupon-id="${coupon.id}" data-loai-phong-id="${loaiPhongId}">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-start mb-2">
                    <div>
                        <span class="badge bg-success mb-2">${coupon.loaiGiamGia || 'DISCOUNT'}</span>
                        <h4 class="card-title">${coupon.maSo}</h4>
                    </div>
                    <div class="text-end">
                        <span class="fs-3 fw-bold text-primary">${coupon.giaTri}${coupon.loaiGiamGia === 'AMOUNT' ? 'VNĐ' : '%'}</span>
                        <p class="text-muted small">GIẢM</p>
                    </div>
                </div>
                <p class="card-text">Áp dụng cho loại phòng này</p>
                <div class="mt-3 small text-muted">
                    <div class="mb-1"><i class="bi bi-calendar-event me-2"></i>Hết hạn: ${coupon.ngayKetThuc}</div>
                    <div><i class="bi bi-wallet2 me-2"></i>Giảm tối đa: ${new Intl.NumberFormat('vi-VN').format(coupon.mucToiDa || 0)} VND</div>
                </div>
            </div>
            <div class="card-footer bg-light d-flex justify-content-between align-items-center">
                <span class="small ${statusClass}">
                    <i class="bi ${statusIcon} me-1"></i> ${statusText}
                </span>
                <button class="btn ${buttonClass} btn-sm" ${buttonDisabled}>
                    Áp dụng
                </button>
            </div>
        </div>
    </div>
    `;
}

function applyCoupon(couponId, chiTietId) {
    const khachHangId = getUserIdFromCookie();

    // noti
    const couponCard = document.querySelector(`.card[data-coupon-id="${couponId}"]`);
    couponCard.classList.add('border-primary');

    fetch('/api/kho-ma-giam-gia/ap-dung', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            maGiamGiaId: couponId,
            chiTietId: chiTietId
        })
    })
    .then(response => {
        if (response.ok) {
            return response.text(); 
        } else {
            throw new Error('Request failed');
        }
    })
    .then(message => {
        alert('Áp dụng mã giảm giá thành công!');

        // close popup
        document.getElementById('couponPopup').style.display = 'none';
        document.getElementById('modalBackdrop').style.display = 'none';
        document.body.classList.remove('modal-open');
        document.body.style.overflow = '';
        document.body.style.paddingRight = '';

        updatePriceDisplay();
    })
    .catch(error => {
        console.error('Lỗi khi áp dụng mã giảm giá:', error);
        alert('Không thể áp dụng mã giảm giá. Vui lòng thử lại sau.');
    });
}

function updatePriceDisplay(data) {
    window.location.reload();
}
