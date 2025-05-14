document.addEventListener('DOMContentLoaded', function () {
    const calendar = document.querySelector('[data-coreui-toggle="calendar"]');
    const checkInInput = document.getElementById('checkIn');
    const checkOutInput = document.getElementById('checkOut');

    calendar.addEventListener('change', function (e) {
        const dates = e.detail;
        if (dates.startDate && dates.endDate) {
            checkInInput.value = dates.startDate.toISOString().split('T')[0];
            checkOutInput.value = dates.endDate.toISOString().split('T')[0];
        }
    });

    // Hàm tạo sao dựa trên điểm đánh giá
    // function renderStarRating() {
    //     const ratingElements = document.querySelectorAll('.star-rating-container');

    //     ratingElements.forEach(container => {
    //         const rating = parseFloat(container.getAttribute('data-rating')) || 0;
    //         const starCount = 5;
    //         let starsHtml = '';

    //         for (let i = 1; i <= starCount; i++) {
    //             if (i <= Math.floor(rating)) {
    //                 starsHtml += '<i class="bi bi-star-fill text-warning"></i>'; // Sao đầy
    //             } else if (i - 0.5 <= rating) {
    //                 starsHtml += '<i class="bi bi-star-half text-warning"></i>'; // Nửa sao
    //             } else {
    //                 starsHtml += '<i class="bi bi-star text-warning"></i>'; // Sao rỗng
    //             }
    //         }

    //         container.innerHTML = starsHtml;
    //     });
    // }

    // Gọi hàm tạo sao khi trang đã tải xong
    renderStarRating();
});

// Sử dụng script từ component SearchBar
// Script đã được tách ra và đặt trong component

// Google Maps API initialization
let map;
let searchBox;
let markers = [];
let staticMap;
let staticMarkers = [];

function initMap() {
    // Khởi tạo bản đồ tĩnh cho phần preview
    staticMap = new google.maps.Map(document.getElementById("staticMapPreview"), {
        center: { lat: 10.346, lng: 107.084 }, // Tọa độ Vũng Tàu
        zoom: 11,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        disableDefaultUI: true,
        scrollwheel: false,
        clickableIcons: false
    });

    // Khởi tạo bản đồ tại vị trí mặc định (Việt Nam)
    map = new google.maps.Map(document.getElementById("mapContainer"), {
        center: { lat: 10.8231, lng: 106.6297 }, // Tọa độ TP.HCM
        zoom: 7,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    // Tạo searchbox cho bản đồ
    const input = document.getElementById("searchTerm");
    searchBox = new google.maps.places.SearchBox(input);

    // Hiển thị tất cả các resort trên cả hai bản đồ
    displayResortsOnMaps();

    // Thêm sự kiện khi người dùng chọn địa điểm từ suggestions
    searchBox.addListener("places_changed", () => {
        const places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }

        // Xóa markers cũ
        clearMarkers();

        // Thêm marker mới cho kết quả tìm được
        const bounds = new google.maps.LatLngBounds();
        places.forEach((place) => {
            if (!place.geometry || !place.geometry.location) {
                console.log("Không tìm thấy thông tin địa điểm");
                return;
            }

            // Tạo marker mới
            markers.push(
                new google.maps.Marker({
                    map,
                    title: place.name,
                    position: place.geometry.location,
                })
            );

            if (place.geometry.viewport) {
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }
        });
        map.fitBounds(bounds);
    });

    // Xử lý sự kiện click vào bản đồ tĩnh
    document.getElementById('mapPreviewContainer').addEventListener('click', function () {
        $('#mapModal').modal('show');
        // Khởi tạo lại bản đồ khi modal hiển thị
        $('#mapModal').on('shown.bs.modal', function () {
            google.maps.event.trigger(map, 'resize');
        });
    });
}

// Hàm hiển thị tất cả các resort trên cả hai bản đồ
function displayResortsOnMaps() {
    // Xóa tất cả markers hiện tại
    clearAllMarkers();

    // Tạo bounds để điều chỉnh zoom của bản đồ
    const bounds = new google.maps.LatLngBounds();
    const staticBounds = new google.maps.LatLngBounds();
    const infoWindow = new google.maps.InfoWindow();

    // Lấy thông tin resort từ dữ liệu trên trang
    const resortElements = document.querySelectorAll('.resort-card');

    if (resortElements.length === 0) {
        return; // Không có resort nào để hiển thị
    }

    resortElements.forEach(function (resortElement) {
        // Lấy thông tin cần thiết từ phần tử resort
        const resortId = resortElement.getAttribute('data-resort-id');
        const resortName = resortElement.querySelector('.property-name').textContent;
        const resortAddress = resortElement.querySelector('.address-text').textContent;
        const resortPrice = resortElement.querySelector('.current-price').textContent;
        const resortImage = resortElement.querySelector('.resort-img').getAttribute('src');

        // Lấy tọa độ từ địa chỉ sử dụng Geocoding service
        const geocoder = new google.maps.Geocoder();
        geocoder.geocode({ 'address': resortAddress }, function (results, status) {
            if (status === 'OK' && results[0]) {
                const position = results[0].geometry.location;

                // Tạo marker cho bản đồ lớn
                const marker = new google.maps.Marker({
                    position: position,
                    map: map,
                    title: resortName,
                    animation: google.maps.Animation.DROP
                });

                // Tạo marker cho bản đồ tĩnh
                const staticMarker = new google.maps.Marker({
                    position: position,
                    map: staticMap,
                    title: resortName
                });

                // Thêm marker vào mảng để quản lý
                markers.push(marker);
                staticMarkers.push(staticMarker);

                // Mở rộng bounds để bao gồm marker này
                bounds.extend(position);
                staticBounds.extend(position);

                // Tạo nội dung cho infowindow
                const content = `
                    <div style="max-width: 300px;">
                        <h5 style="margin-bottom: 8px;">${resortName}</h5>
                        <div style="display: flex; margin-bottom: 8px;">
                            <img src="${resortImage}" style="width: 80px; height: 60px; object-fit: cover; margin-right: 10px;">
                            <div>
                                <p style="margin: 0; font-size: 12px;"><i class="bi bi-geo-alt-fill"></i> ${resortAddress}</p>
                                <p style="margin: 5px 0; font-weight: bold; color: #0071c2;">${resortPrice}</p>
                            </div>
                        </div>
                        <a href="/resort-detail/${resortId}" class="btn btn-sm btn-primary" style="width: 100%;">Xem chi tiết</a>
                    </div>
                `;

                // Thêm sự kiện click cho marker
                marker.addListener('click', function () {
                    infoWindow.setContent(content);
                    infoWindow.open(map, marker);
                });

                staticMarker.addListener('click', function () {
                    // Khi click vào marker trên bản đồ tĩnh, mở modal và focus vào marker tương ứng
                    $('#mapModal').modal('show');
                    setTimeout(function () {
                        infoWindow.setContent(content);
                        infoWindow.open(map, marker);
                        map.setCenter(position);
                        map.setZoom(15);
                    }, 500);
                });

                // Điều chỉnh zoom và trung tâm của bản đồ
                if (resortElements.length > 1) {
                    map.fitBounds(bounds);
                    staticMap.fitBounds(staticBounds);
                } else {
                    map.setCenter(position);
                    map.setZoom(15);
                    staticMap.setCenter(position);
                    staticMap.setZoom(15);
                }
            }
        });
    });
}

// Hàm xóa tất cả markers
function clearAllMarkers() {
    // Xóa markers trên bản đồ chính
    markers.forEach(marker => {
        marker.setMap(null);
    });
    markers = [];

    // Xóa markers trên bản đồ tĩnh
    staticMarkers.forEach(marker => {
        marker.setMap(null);
    });
    staticMarkers = [];
}

// Xử lý sự kiện khi người dùng nhấn vào địa chỉ resort để xem trên bản đồ
$(document).ready(function () {
    // Xử lý click vào địa chỉ resort
    $('.address-text').on('click', function (e) {
        e.stopPropagation(); // Ngăn không cho sự kiện click lan đến thẻ cha

        const resortCard = $(this).closest('.resort-card');
        const resortId = resortCard.data('resort-id');
        const resortAddress = $(this).text();

        // Mở modal bản đồ
        $('#mapModal').modal('show');

        // Sau khi modal hiển thị, tìm và focus vào marker của resort được chọn
        $('#mapModal').on('shown.bs.modal', function () {
            google.maps.event.trigger(map, 'resize');

            // Tìm marker dựa trên resort ID
            const geocoder = new google.maps.Geocoder();
            geocoder.geocode({ 'address': resortAddress }, function (results, status) {
                if (status === 'OK' && results[0]) {
                    const position = results[0].geometry.location;
                    map.setCenter(position);
                    map.setZoom(15);

                    // Tìm marker tương ứng và kích hoạt sự kiện click
                    markers.forEach(function (marker) {
                        if (marker.getPosition().equals(position)) {
                            google.maps.event.trigger(marker, 'click');
                        }
                    });
                }
            });
        });
    });
});