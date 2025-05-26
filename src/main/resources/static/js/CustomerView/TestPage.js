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
    // Tự động điền userId từ cookie vào input field
    const userId = getUserIdFromCookie();
    if (userId) {
        $('#khachHangId').val(userId);
    }

    // Cập nhật số lượng mục trong giỏ hàng
    updateCartCount();

    // Initialize gallery functionality
    initializeGallery();

    // Initialize gallery popup functionality
    initializeGalleryPopup();

    // Initialize add to cart functionality
    initializeAddToCart();

    // Initialize rating functionality
    initializeRating();
});

// Gallery functionality
function initializeGallery() {
    // Add click handlers to gallery thumbnails
    document.querySelectorAll('.gallery-thumbnail').forEach(thumbnail => {
        thumbnail.addEventListener('click', function () {
            const imageUrl = this.dataset.imageUrl;
            if (imageUrl) {
                changeMainImage(imageUrl);
            }
        });
    });

    // Initialize first thumbnail as active
    const firstThumbnail = document.querySelector('.gallery-thumbnail');
    if (firstThumbnail) {
        firstThumbnail.classList.add('active-thumbnail');
    }
}

// Change main image when thumbnail is clicked
function changeMainImage(src, clickedElement = null) {
    const mainImage = document.getElementById('main-image');
    if (mainImage) {
        mainImage.src = src;

        // Remove active class from all thumbnails
        const thumbnails = document.querySelectorAll('.gallery-thumbnail');
        thumbnails.forEach(thumb => {
            thumb.classList.remove('active-thumbnail');
        });

        // Add active class to clicked thumbnail
        if (clickedElement) {
            clickedElement.classList.add('active-thumbnail');
        } else {
            // Find thumbnail with matching image URL
            thumbnails.forEach(thumb => {
                if (thumb.dataset.imageUrl === src) {
                    thumb.classList.add('active-thumbnail');
                }
            });
        }
    }
}

// Add to cart functionality
function initializeAddToCart() {
    // Gắn sự kiện click cho tất cả các nút có class add-to-cart
    $(document).on('click', '.add-to-cart', function (event) {
        event.preventDefault();

        // Lấy dữ liệu từ thuộc tính data-*
        const button = $(this);
        const roomTypeId = button.data('room-type-id');
        let packageId = button.data('package-id');

        // Lấy thông tin ngày từ search bar hoặc URL parameters
        const checkInInput = document.getElementById('checkIn');
        const checkOutInput = document.getElementById('checkOut');

        let checkIn = checkInInput ? checkInInput.value : null;
        let checkOut = checkOutInput ? checkOutInput.value : null;

        // Nếu không có ngày từ form, lấy từ URL parameters
        if (!checkIn || !checkOut) {
            const urlParams = new URLSearchParams(window.location.search);
            checkIn = urlParams.get('checkIn') || moment().format('YYYY-MM-DD');
            checkOut = urlParams.get('checkOut') || moment().add(1, 'day').format('YYYY-MM-DD');
        }

        // Nếu packageId là "null" (string), chuyển thành null (object)
        if (packageId === "null" || packageId === undefined) {
            packageId = null;
        }

        // Tạo đối tượng dữ liệu để gửi đi
        const data = {
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
                // Hiển thị thông báo thành công với Bootstrap toast
                showSuccessToast('Đã thêm vào giỏ hàng thành công!');
                // Cập nhật số lượng mục trong giỏ hàng
                updateCartCount();
            } else {
                // Hiển thị thông báo lỗi
                showErrorToast('Có lỗi xảy ra khi thêm vào giỏ hàng!');
            }
        })
        .catch(error => {
            console.error('Lỗi:', error);
            showErrorToast('Có lỗi xảy ra khi thêm vào giỏ hàng!');
        });
    });
}

// Rating functionality
function initializeRating() {
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
}

// Hàm cập nhật số lượng mục trong giỏ hàng
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
            console.log('Số lượng mục trong giỏ hàng:', data.count);
        })
        .catch(error => {
            console.error('Lỗi khi lấy số lượng mục trong giỏ hàng:', error);
        });
}

// Toast notification functions
function showSuccessToast(message) {
    showToast(message, 'success');
}

function showErrorToast(message) {
    showToast(message, 'danger');
}

function showToast(message, type) {
    // Create toast element
    const toastId = 'toast-' + Date.now();
    const toastHtml = `
        <div id="${toastId}" class="toast align-items-center text-white bg-${type} border-0" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body">
                    ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    `;

    // Add toast to container
    let toastContainer = document.getElementById('toast-container');
    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.id = 'toast-container';
        toastContainer.className = 'toast-container position-fixed bottom-0 end-0 p-3';
        toastContainer.style.zIndex = '1055';
        document.body.appendChild(toastContainer);
    }

    toastContainer.insertAdjacentHTML('beforeend', toastHtml);

    // Show toast
    const toastElement = document.getElementById(toastId);
    const toast = new bootstrap.Toast(toastElement, {
        autohide: true,
        delay: 3000
    });
    toast.show();

    // Remove toast element after it's hidden
    toastElement.addEventListener('hidden.bs.toast', function () {
        toastElement.remove();
    });
}

// Virtual tour functionality (if needed)
function initializeVirtualTour() {
    const openPopupBtn = document.getElementById('open-popup-btn');
    const closePopupBtn = document.getElementById('close-popup');
    const popup = document.getElementById('popup');
    const iframePopup = document.getElementById('iframe-popup');
    const resortData = document.getElementById('resortData');

    if (openPopupBtn && popup && iframePopup && resortData) {
        openPopupBtn.addEventListener('click', function() {
            var img360Url = resortData.getAttribute('data-img360-url');

            window.scrollTo(0, 0);

            // Set the iframe src to img360Url
            iframePopup.src = img360Url;

            // Show the popup
            popup.style.display = 'flex';

            // Prevent body scrolling when popup is open
            document.body.style.overflow = 'hidden';
        });

        if (closePopupBtn) {
            closePopupBtn.addEventListener('click', function() {
                // Close the popup and clear the iframe source
                popup.style.display = 'none';
                iframePopup.src = '';

                // Restore body scrolling
                document.body.style.overflow = '';
            });
        }
    }
}

// Gallery popup functionality
function initializeGalleryPopup() {
    const viewAllBtn = document.getElementById('view-all-gallery-btn');
    const galleryPopup = document.getElementById('gallery-popup');
    const closeGalleryBtn = document.getElementById('close-gallery-popup');
    const galleryGrid = document.getElementById('gallery-grid');
    const resortData = document.getElementById('resortData');

    console.log('Initializing gallery popup...');
    console.log('viewAllBtn:', viewAllBtn);
    console.log('galleryPopup:', galleryPopup);
    console.log('closeGalleryBtn:', closeGalleryBtn);
    console.log('galleryGrid:', galleryGrid);
    console.log('resortData:', resortData);

    // Always create fallback images for testing
    const fallbackImages = [
        'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80',
        'https://images.unsplash.com/photo-1551632436-cbf8dd35adfa?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2071&q=80',
        'https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80',
        'https://images.unsplash.com/photo-1571896349842-33c89424de2d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2080&q=80',
        'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80',
        'https://images.unsplash.com/photo-1564501049412-61c2a3083791?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80'
    ];

    let allImages = [];

    if (resortData) {
        // Get images data from HTML
        const imagesData = resortData.getAttribute('data-images');
        const imageUrls = imagesData ? imagesData.split('|').filter(url => url.trim() !== '') : [];
        console.log('Images from data:', imageUrls);
        allImages = [...imageUrls, ...fallbackImages];
    } else {
        allImages = fallbackImages;
    }

    console.log('All images:', allImages);

    // If viewAllBtn exists, add event listener
    if (viewAllBtn) {
        console.log('Adding click event to view all button');
        viewAllBtn.addEventListener('click', function(e) {
            e.preventDefault();
            console.log('View all button clicked!');

            if (galleryPopup && galleryGrid) {
                // Clear existing images
                galleryGrid.innerHTML = '';

                // Populate gallery grid
                allImages.forEach((imageUrl, index) => {
                    const gridItem = document.createElement('div');
                    gridItem.className = 'gallery-grid-item';
                    gridItem.innerHTML = `
                        <img src="${imageUrl}" alt="Resort image ${index + 1}" onclick="changeMainImageFromPopup('${imageUrl}')">
                    `;
                    galleryGrid.appendChild(gridItem);
                });

                // Show popup
                galleryPopup.style.display = 'flex';
                document.body.style.overflow = 'hidden';
                console.log('Popup should be visible now');
            }
        });
    }

    // Close gallery popup
    if (closeGalleryBtn && galleryPopup) {
        closeGalleryBtn.addEventListener('click', function() {
            galleryPopup.style.display = 'none';
            document.body.style.overflow = '';
        });
    }

    // Close popup when clicking outside
    if (galleryPopup) {
        galleryPopup.addEventListener('click', function(e) {
            if (e.target === galleryPopup) {
                galleryPopup.style.display = 'none';
                document.body.style.overflow = '';
            }
        });
    }
}

// Change main image from popup and close popup
function changeMainImageFromPopup(imageUrl) {
    const mainImage = document.getElementById('main-image');
    if (mainImage) {
        mainImage.src = imageUrl;
    }

    // Close popup
    const galleryPopup = document.getElementById('gallery-popup');
    if (galleryPopup) {
        galleryPopup.style.display = 'none';
        document.body.style.overflow = '';
    }

    // Update active thumbnail
    const thumbnails = document.querySelectorAll('.gallery-thumbnail');
    thumbnails.forEach(thumb => {
        thumb.classList.remove('active-thumbnail');
        if (thumb.dataset.imageUrl === imageUrl) {
            thumb.classList.add('active-thumbnail');
        }
    });
}

// Global function to open gallery popup (can be called from onclick)
window.openGalleryPopup = function() {
    console.log('openGalleryPopup called');

    const galleryPopup = document.getElementById('gallery-popup');
    const galleryGrid = document.getElementById('gallery-grid');
    const resortData = document.getElementById('resortData');

    if (!galleryPopup || !galleryGrid) {
        console.error('Gallery popup elements not found');
        return;
    }

    // Clear existing images
    galleryGrid.innerHTML = '';

    // Get images from data attribute
    let imageUrls = [];
    if (resortData) {
        const imagesData = resortData.getAttribute('data-images');
        imageUrls = imagesData ? imagesData.split('|').filter(url => url.trim() !== '') : [];
    }

    // Add fallback images
    const fallbackImages = [
        'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80',
        'https://images.unsplash.com/photo-1551632436-cbf8dd35adfa?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2071&q=80',
        'https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80',
        'https://images.unsplash.com/photo-1571896349842-33c89424de2d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2080&q=80',
        'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80',
        'https://images.unsplash.com/photo-1564501049412-61c2a3083791?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80'
    ];

    const allImages = [...imageUrls, ...fallbackImages];
    console.log('Total images to display:', allImages.length);

    // Create image elements
    allImages.forEach((imageUrl, index) => {
        console.log('Adding image:', imageUrl);
        const gridItem = document.createElement('div');
        gridItem.className = 'gallery-grid-item';

        const img = document.createElement('img');
        img.src = imageUrl;
        img.alt = `Resort image ${index + 1}`;
        img.onclick = function() {
            changeMainImageFromPopup(imageUrl);
        };

        gridItem.appendChild(img);
        galleryGrid.appendChild(gridItem);
    });

    // Show popup
    galleryPopup.style.display = 'flex';
    document.body.style.overflow = 'hidden';

    console.log('Gallery popup opened with', allImages.length, 'images');
}

// Initialize virtual tour when DOM is ready
$(document).ready(function() {
    initializeVirtualTour();
});
