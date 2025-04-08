document.addEventListener("DOMContentLoaded", function () {
    const searchDropdown = document.getElementById("searchName"); // Dropdown chọn khu nghỉ dưỡng
    //const roomRows = document.querySelectorAll("#resortList tr"); // Danh sách hàng (loại phòng)

    searchDropdown.addEventListener("change", function () {
        const selectedResortId = searchDropdown.value; // ID khu nghỉ dưỡng đã chọn

        const roomRows = document.querySelectorAll("#resortList tr");

        roomRows.forEach(row => {
            const hiddenCell = row.querySelector("td[data-id]");
            if (!hiddenCell) return;

            const roomResortId = hiddenCell.getAttribute("data-id");

            if (!selectedResortId || selectedResortId === roomResortId) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
        
    });

    // Insert Form
    document.getElementById("insertForm").addEventListener("submit", function (e) {
        e.preventDefault();

        const newRoomType = {
            tenLoaiPhong: document.getElementById("roomTypeName").value,
            dienTich: parseFloat(document.getElementById("acreage").value),
            loaiPhongTheoTieuChuan: document.getElementById("qualityStandard").value,
            loaiPhongTheoSoLuong: document.getElementById("quantityStandard").value,
            soGiuong: parseInt(document.getElementById("numberOfBed").value),
            soNguoi: parseInt(document.getElementById("numberOfPeople").value),
            gia: parseFloat(document.getElementById("roomTypePrice").value),
            khuNghiDuongId: parseInt(document.getElementById("resortId").value)
        };

        fetch(`http://localhost:8080/api/resort/room-type`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newRoomType)
        })
        .then(res => {
            if (!res.ok) throw new Error("Insert failed");
            alert("Thêm thành công!");
            window.location.reload();
        })
        .catch(() => alert("Có lỗi xảy ra khi thêm"));
    });


    // DELETE
    document.getElementById("deleteBtn").addEventListener("click", function () {
        const checkedBoxes = document.querySelectorAll(".selectResort:checked");
        if (checkedBoxes.length === 0) {
            alert("Vui lòng chọn ít nhất một loại phòng để xoá.");
            return;
        }

        if (!confirm("Bạn có chắc chắn muốn xoá?")) return;

        const idsToDelete = {
            ids: Array.from(checkedBoxes).map(box => parseInt(box.dataset.id))
        };
        fetch(`http://localhost:8080/api/resort/room-type`, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(idsToDelete)
        })
        .then(res => {
            if (!res.ok) throw new Error("Delete failed");
            alert("Xoá thành công!");
            window.location.reload();
        })
        .catch(() => alert("Xoá thất bại"));
    });
    

    // Update form
    // Show form Update khi chỉ chọn một dòng
    const updateBtn = document.querySelector(".btn-warning");
    const updateFormContainer = document.getElementById("updateFormContainer");
    const cancelUpdateBtn = document.getElementById("cancelUpdateBtn");
    const checkedBoxes = document.querySelectorAll(".selectResort:checked");
    updateBtn.addEventListener("click", function () {
        const checkedBoxes = document.querySelectorAll(".selectResort:checked");

        if (checkedBoxes.length !== 1) {
            alert("Vui lòng chọn một loại phòng để cập nhật.");
            return;
        }

        const selectedRow = checkedBoxes[0].closest("tr");

        // Lấy dữ liệu từ bảng
        const tenLoaiPhong = selectedRow.children[1].textContent.trim();
        const dienTich = parseFloat(selectedRow.children[2].textContent.trim());
        const loaiTieuChuan = selectedRow.children[3].textContent.trim();
        const loaiSoLuong = selectedRow.children[4].textContent.trim();
        const soGiuong = parseInt(selectedRow.children[5].textContent.trim());
        const soNguoi = parseInt(selectedRow.children[6].textContent.trim());
        const gia = parseFloat(selectedRow.children[7].textContent.trim());
        const resortID = parseInt(selectedRow.children[9].getAttribute("data-id").trim());
        if (isNaN(dienTich) || isNaN(soGiuong) || isNaN(soNguoi) || isNaN(gia) || isNaN(resortID)) {
            alert("Dữ liệu số không hợp lệ.");
            return;
        }
        const updateResortID = selectedRow.children[9].getAttribute("data-id");
        // Gán dữ liệu vào form update
        document.getElementById("updateResortID").value = updateResortID;
        document.getElementById("updateRoomTypeName").value = tenLoaiPhong;
        document.getElementById("updateAcreage").value = dienTich;
        document.getElementById("updateQualityStandard").value = loaiTieuChuan;
        document.getElementById("updateQuantityStandard").value = loaiSoLuong;
        document.getElementById("updateNumberOfBed").value = soGiuong;
        document.getElementById("updateNumberOfPeople").value = soNguoi;
        document.getElementById("updateRoomTypePrice").value = gia;

        // Hiển thị form cập nhật
        updateFormContainer.style.display = "block";
    });


    // Khi bấm vào nút Cancel trong form cập nhật
    cancelUpdateBtn.addEventListener("click", function () {
        updateFormContainer.style.display = "none"; // Ẩn form cập nhật
    });

    // Submit Update form
    document.getElementById("updateForm").addEventListener("submit", function (e) {
        e.preventDefault();
        const checkedBoxes = document.querySelectorAll(".selectResort:checked");
        const updatedRoomTypeID = checkedBoxes[0].getAttribute("data-id");
        const updatedRoomType = {
            id: parseInt(updatedRoomTypeID),
            khuNghiDuongId: parseInt(document.getElementById("updateResortID").value),
            tenLoaiPhong: document.getElementById("updateRoomTypeName").value,
            dienTich: parseFloat(document.getElementById("updateAcreage").value),
            loaiPhongTheoTieuChuan: document.getElementById("updateQualityStandard").value,
            loaiPhongTheoSoLuong: document.getElementById("updateQuantityStandard").value,
            soGiuong: parseInt(document.getElementById("updateNumberOfBed").value),
            soNguoi: parseInt(document.getElementById("updateNumberOfPeople").value),
            gia: parseFloat(document.getElementById("updateRoomTypePrice").value)
        };

        fetch(`http://localhost:8080/api/resort/room-type/${updatedRoomType.id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(updatedRoomType)
        })
        .then(res => {
            if (!res.ok) throw new Error("Update failed");
            alert("Cập nhật thành công!");
            window.location.reload();
        })
        .catch(() => alert("Có lỗi khi cập nhật"));
    });

    // Cancel Update
    document.getElementById("cancelUpdateBtn").addEventListener("click", function () {
        document.getElementById("updateFormContainer").style.display = "none";
    });

});
