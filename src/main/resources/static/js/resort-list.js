document.addEventListener("DOMContentLoaded", function() {

    // Chỗ này có thể update
    // Sample dynamic data for cities, districts, and provinces
    const cities = ["Hanoi", "Hồ Chí Minh", "Da Nang"];
    const districts = {
        "Hanoi": ["District 1", "District 2"],
        "Hồ Chí Minh": ["Quận 1", "District 4"],
        "Da Nang": ["District 5", "District 6"]
    };
    const provinces = {
        "Hanoi": ["Province 1", "Province 2"],
        "Hồ Chí Minh": ["Bến Thành", "Province 4"],
        "Da Nang": ["Province 5", "Province 6"]
    };

    // Populate city dropdown on page load
    cities.forEach(function(city) {
        const option = document.createElement("option");
        option.value = city;
        option.textContent = city;
        document.getElementById('city').appendChild(option);
    });

    // When city is selected, populate district and province dropdowns
    document.getElementById('city').addEventListener('change', function() {
        const city = this.value;
        const districtSelect = document.getElementById('district');
        const provinceSelect = document.getElementById('province');

        // Clear the existing options
        districtSelect.innerHTML = "<option value=''>Select District</option>";
        provinceSelect.innerHTML = "<option value=''>Select Province</option>";

        if (city) {
            districts[city].forEach(function(district) {
                const option = document.createElement("option");
                option.value = district;
                option.textContent = district;
                districtSelect.appendChild(option);
            });
            provinces[city].forEach(function(province) {
                const option = document.createElement("option");
                option.value = province;
                option.textContent = province;
                provinceSelect.appendChild(option);
            });
        }
    });

    // Show the form when Insert button is clicked
    document.getElementById('insertBtn').addEventListener('click', function() {
        document.getElementById('insertFormContainer').style.display = 'block';
    });

    // Hide the form when Cancel button is clicked
    document.getElementById('cancelBtn').addEventListener('click', function() {
        document.getElementById('insertFormContainer').style.display = 'none';
    });

    // Handle form submission
    document.getElementById('insertForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const resortName = document.getElementById('resortName').value;
        const address = document.getElementById('address').value;
        const city = document.getElementById('city').value;
        const district = document.getElementById('district').value;
        const province = document.getElementById('province').value;

        // Prepare the data to be sent
        const data = {
            resortName,
            address,
            city,
            district,
            province
        };

        console.log(city, district, province);

        const utf8EncodedData = JSON.stringify(data);
        // Use the Fetch API to send the data to the backend
        fetch('http://localhost:8080/api/resort/insert', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            body: utf8EncodedData
        })
        .then(response => response.json())
        .then(data => {
            alert('Resort successfully added!');
            // Optionally, refresh the resort list or hide the form
            document.getElementById('insertFormContainer').style.display = 'none';
            // Reload or update table here if needed
        })
        .catch(error => {
            alert('Failed to add resort.');
            console.error(error);
        });
    });
});


document.addEventListener("DOMContentLoaded", function() {
    const updateBtn = document.getElementById("updateBtn");
    const formContainer = document.getElementById("insertFormContainer");
    const cancelBtn = document.getElementById("cancelBtn");
    const submitBtn = document.querySelector("#insertForm button[type='submit']");
    const table = document.querySelector("table tbody");

    // Hàm kiểm tra và cập nhật trạng thái nút Update
    function updateButtonState() {
        const selectedRows = document.querySelectorAll('input[type="checkbox"]:checked');
        updateBtn.disabled = selectedRows.length !== 1; // Nếu có đúng 1 checkbox được chọn, kích hoạt nút Update
    }

    // Cập nhật trạng thái nút "Update" mỗi khi người dùng chọn hoặc bỏ chọn checkbox
    table.addEventListener("change", function() {
        updateButtonState();
    });

    // Hiển thị form insert khi bấm "Update"
    updateBtn.addEventListener("click", function() {
        const selectedRows = document.querySelectorAll('input[type="checkbox"]:checked');

        if (selectedRows.length === 1) {
            const row = selectedRows[0].closest("tr");
            const resortName = row.querySelector("td:nth-child(2)").textContent.trim();
            const address = row.querySelector("td:nth-child(3)").textContent.trim();
            const rating = row.querySelector("td:nth-child(4)").textContent.trim();
            const resortId = row.querySelector('input[type="checkbox"]').getAttribute("data-id");

            // Hiển thị form và điền thông tin vào các trường
            formContainer.style.display = 'block';
            document.getElementById("resortName").value = resortName;
            document.getElementById("address").value = address;
            document.getElementById("rating").value = rating;

            // Cập nhật thông tin khi người dùng nhấn submit
            submitBtn.addEventListener("click", function(e) {
                e.preventDefault(); // Ngừng form submit tự động

                const updatedResort = {
                    ten: document.getElementById("resortName").value,
                    diaChi: document.getElementById("address").value,
                    danhGia: parseInt(document.getElementById("rating").value)
                };

                // Gửi PATCH request để cập nhật resort
                fetch(`/khuNghiDuong/${resortId}`, {
                    method: 'PATCH',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(updatedResort)
                })
                .then(response => response.json())
                .then(updatedData => {
                    if (updatedData) {
                        // Cập nhật lại dữ liệu trong bảng
                        row.querySelector("td:nth-child(2)").textContent = updatedData.ten;
                        row.querySelector("td:nth-child(3)").textContent = updatedData.diaChi;
                        row.querySelector("td:nth-child(4)").textContent = updatedData.danhGia;

                        // Ẩn form insert
                        formContainer.style.display = 'none';
                    } else {
                        alert("Cập nhật không thành công.");
                    }
                })
                .catch(error => {
                    console.error("Error updating resort:", error);
                    alert("Đã có lỗi xảy ra.");
                });
            });
        } else {
            alert("Vui lòng chọn chỉ 1 resort để cập nhật.");
        }
    });

    // Hủy bỏ và ẩn form insert
    cancelBtn.addEventListener("click", function() {
        formContainer.style.display = 'none';
    });

    // Ban đầu, kiểm tra trạng thái của nút Update
    updateButtonState();
});
