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


    
});