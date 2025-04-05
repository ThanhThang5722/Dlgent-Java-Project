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


    /*const updateBtn = document.querySelector(".btn-warning");
    const updateFormContainer = document.getElementById("updateFormContainer");
    const cancelUpdateBtn = document.getElementById("cancelUpdateBtn");
    updateBtn.addEventListener("click", function () {
        updateFormContainer.style.display = "block";
    });
    cancelUpdateBtn.addEventListener("click", function () {
        updateFormContainer.style.display = "none";
    });*/

    
    // Lấy các phần tử cần thiết
    const updateBtn = document.querySelector(".btn-warning");
    const updateFormContainer = document.getElementById("updateFormContainer");
    const cancelUpdateBtn = document.getElementById("cancelUpdateBtn");
    const updateResortName = document.getElementById("updateResortName");
    const updateAddress = document.getElementById("updateAddress");

    // Khi bấm vào nút Update
    updateBtn.addEventListener("click", function () {
        // Tìm tất cả checkbox đang được chọn
        const checkedBoxes = document.querySelectorAll(".selectResort:checked");

        // Kiểm tra xem có đúng 1 checkbox được chọn không
        if (checkedBoxes.length !== 1) {
            alert("Vui lòng chọn một khu nghỉ dưỡng để cập nhật.");
            return;
        }

        // Lấy hàng (row) chứa thông tin của khu nghỉ dưỡng được chọn
        const selectedRow = checkedBoxes[0].closest("tr");

        // Lấy thông tin từ hàng đó
        const name = selectedRow.children[1].textContent.trim(); // Cột thứ 2: Tên khu nghỉ dưỡng
        const address = selectedRow.children[2].textContent.trim(); // Cột thứ 3: Địa chỉ

        // Điền thông tin vào form cập nhật
        updateResortName.value = name;
        updateAddress.value = address;

        // Hiển thị form cập nhật
        updateFormContainer.style.display = "block";
    });

    // Khi bấm vào nút Cancel trong form cập nhật
    cancelUpdateBtn.addEventListener("click", function () {
        updateFormContainer.style.display = "none"; // Ẩn form cập nhật
    });

    const updateForm = document.getElementById("updateForm");

    updateForm.addEventListener("submit", function (event) {
        event.preventDefault(); // Ngăn chặn load lại trang

        // Lấy checkbox được chọn
        const checkedBox = document.querySelector(".selectResort:checked");
        if (!checkedBox) {
            alert("Vui lòng chọn một khu nghỉ dưỡng để cập nhật.");
            return;
        }

        // Lấy ID của khu nghỉ dưỡng từ thuộc tính data-id của checkbox
        const resortId = checkedBox.getAttribute("data-id");

        // Lấy thông tin từ form cập nhật
        const updatedData = {
            ten: document.getElementById("updateResortName").value.trim(),
            diaChi: document.getElementById("updateAddress").value.trim(),
        };

        console.log(updatedData);

        // Gửi PUT request đến API
        fetch(`http://localhost:8080/api/resort/${resortId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(updatedData),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Cập nhật thất bại!");
            }
            return response.json();
        })
        .then(data => {
            alert("Cập nhật thành công!");
            location.reload(); // Làm mới trang sau khi cập nhật
        })
        .catch(error => {
            alert("Có lỗi xảy ra: " + error.message);
        });
    });


    const deleteBtn = document.getElementById("deleteBtn");

    deleteBtn.addEventListener("click", function () {
        // Lấy tất cả checkbox được chọn
        const checkedBoxes = document.querySelectorAll(".selectResort:checked");

        if (checkedBoxes.length === 0) {
            alert("Vui lòng chọn ít nhất một khu nghỉ dưỡng để xóa.");
            return;
        }

        // Xác nhận trước khi xóa
        if (!confirm("Bạn có chắc chắn muốn xóa các khu nghỉ dưỡng đã chọn?")) {
            return;
        }

        // Lấy danh sách ID từ checkbox
        const resortIds = Array.from(checkedBoxes).map(checkbox => checkbox.getAttribute("data-id"));

        // Gửi DELETE request đến API
        fetch("http://localhost:8080/api/resort", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(resortIds), // Gửi danh sách ID dưới dạng JSON
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Xóa thất bại!");
            }
            return response.json();
        })
        .then(data => {
            alert("Xóa thành công!");
            location.reload(); // Làm mới trang sau khi xóa
        })
        .catch(error => {
            alert("Có lỗi xảy ra: " + error.message);
        });
    });
});