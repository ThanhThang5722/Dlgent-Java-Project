document.addEventListener('DOMContentLoaded', function () {
    loadPartners();

    // Xử lý tìm kiếm
    document.getElementById('searchBtn').addEventListener('click', function () {
        loadPartners(document.getElementById('searchInput').value);
    });

    // Xử lý lưu thay đổi
    document.getElementById('saveChanges').addEventListener('click', function () {
        updatePartner();
    });
});

function loadPartners(searchTerm = '') {
    fetch('/api/admin/partner-account')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('partnerTableBody');
            tableBody.innerHTML = '';

            // Lọc dữ liệu theo từ khóa tìm kiếm
            let filteredData = data;

            // Lọc theo từ khóa tìm kiếm nếu có
            if (searchTerm) {
                filteredData = filteredData.filter(partner =>
                    partner.hoTen.toLowerCase().includes(searchTerm.toLowerCase())
                );
            }

            filteredData.forEach(partner => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${partner.doiTacId}</td>
                    <td>${partner.hoTen}</td>
                    <td>${partner.email}</td>
                    <td>${partner.sdt || '-'}</td>
                    <td>${partner.isDeleted === 1 ? 'Không hoạt động' : (partner.trangThai === 'ACTIVE' ? 'Hoạt động' : 'Không hoạt động')}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="editPartner(${partner.doiTacId})">
                            <i class="bi bi-pencil"></i> Sửa
                        </button>
                        ${partner.isDeleted !== 1 ?
                        `<button class="btn btn-sm btn-danger" onclick="deletePartner(${partner.doiTacId})">
                            <i class="bi bi-trash"></i> Xóa
                        </button>` : ''}
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error loading partners:', error);
            alert('Không thể tải dữ liệu đối tác. Vui lòng thử lại sau.');
        });
}

function editPartner(id) {
    fetch(`/api/admin/partner-account/${id}`)
        .then(response => response.json())
        .then(partner => {
            document.getElementById('editId').value = partner.doiTacId;
            document.getElementById('editName').value = partner.hoTen;
            document.getElementById('editEmail').value = partner.email;
            document.getElementById('editPhone').value = partner.sdt || '';
            document.getElementById('editAddress').value = partner.diaChi || '';
            document.getElementById('editStatus').value = partner.trangThai;

            // Nếu tài khoản đã bị xóa, hiển thị thông báo
            if (partner.isDeleted === 1) {
                alert('Tài khoản này đã bị xóa. Bạn có thể xem nhưng không thể chỉnh sửa.');
                document.getElementById('saveChanges').style.display = 'none';
            } else {
                document.getElementById('saveChanges').style.display = 'block';
            }

            const modal = new bootstrap.Modal(document.getElementById('editModal'));
            modal.show();
        })
        .catch(error => {
            console.error('Error fetching partner details:', error);
            alert('Không thể tải thông tin đối tác. Vui lòng thử lại sau.');
        });
}

function updatePartner() {
    const id = document.getElementById('editId').value;
    const partnerData = {
        doiTacId: id,
        hoTen: document.getElementById('editName').value,
        email: document.getElementById('editEmail').value,
        sdt: document.getElementById('editPhone').value,
        diaChi: document.getElementById('editAddress').value,
        trangThai: document.getElementById('editStatus').value,
        isDeleted: 0 // Ensure the partner is not marked as deleted
    };

    fetch(`/api/admin/partner-account/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(partnerData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // The server might return text instead of JSON
            return response.text().then(text => {
                try {
                    // Try to parse as JSON if possible
                    return text ? JSON.parse(text) : {};
                } catch (e) {
                    // If not JSON, just return the text
                    return text;
                }
            });
        })
        .then(data => {
            alert('Cập nhật thông tin đối tác thành công!');
            const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
            modal.hide();
            loadPartners();
        })
        .catch(error => {
            console.error('Error updating partner:', error);
            alert('Không thể cập nhật thông tin đối tác. Vui lòng thử lại sau.');
        });
}

function deletePartner(id) {
    if (confirm('Bạn có chắc chắn muốn xóa đối tác này?')) {
        fetch(`/api/admin/partner-account/${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                // The server might return text instead of JSON
                return response.text().then(text => {
                    try {
                        // Try to parse as JSON if possible
                        return text ? JSON.parse(text) : {};
                    } catch (e) {
                        // If not JSON, just return the text
                        return text;
                    }
                });
            })
            .then(data => {
                alert('Xóa đối tác thành công!');
                loadPartners();
            })
            .catch(error => {
                console.error('Error deleting partner:', error);
                alert('Không thể xóa đối tác. Vui lòng thử lại sau.');
            });
    }
}
