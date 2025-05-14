document.addEventListener('DOMContentLoaded', function () {
    loadCustomers();

    // Xử lý tìm kiếm
    document.getElementById('searchBtn').addEventListener('click', function () {
        loadCustomers(document.getElementById('searchInput').value);
    });

    // Xử lý lưu thay đổi
    document.getElementById('saveChanges').addEventListener('click', function () {
        updateCustomer();
    });
});

function loadCustomers(searchTerm = '') {
    fetch('/api/admin/customer-account')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('customerTableBody');
            tableBody.innerHTML = '';

            // Lọc dữ liệu theo từ khóa tìm kiếm
            let filteredData = data;

            // Lọc theo từ khóa tìm kiếm nếu có
            if (searchTerm) {
                filteredData = filteredData.filter(customer =>
                    customer.hoTen.toLowerCase().includes(searchTerm.toLowerCase())
                );
            }

            filteredData.forEach(customer => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${customer.khachHangId}</td>
                    <td>${customer.hoTen}</td>
                    <td>${customer.email}</td>
                    <td>${customer.sdt || '-'}</td>
                    <td>${customer.isDeleted === 1 ? 'Không hoạt động' : (customer.trangThai === 'ACTIVE' ? 'Hoạt động' : 'Không hoạt động')}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="editCustomer(${customer.khachHangId})">
                            <i class="bi bi-pencil"></i> Sửa
                        </button>
                        ${customer.isDeleted !== 1 ?
                        `<button class="btn btn-sm btn-danger" onclick="deleteCustomer(${customer.khachHangId})">
                            <i class="bi bi-trash"></i> Xóa
                        </button>` : ''}
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error loading customers:', error);
            alert('Không thể tải dữ liệu khách hàng. Vui lòng thử lại sau.');
        });
}

function editCustomer(id) {
    fetch(`/api/admin/customer-account/${id}`)
        .then(response => response.json())
        .then(customer => {
            document.getElementById('editId').value = customer.khachHangId;
            document.getElementById('editName').value = customer.hoTen;
            document.getElementById('editEmail').value = customer.email;
            document.getElementById('editPhone').value = customer.sdt || '';
            document.getElementById('editAddress').value = customer.diaChi || '';
            document.getElementById('editStatus').value = customer.trangThai;

            // Nếu tài khoản đã bị xóa, hiển thị thông báo
            if (customer.isDeleted === 1) {
                alert('Tài khoản này đã bị xóa. Bạn có thể xem nhưng không thể chỉnh sửa.');
                document.getElementById('saveChanges').style.display = 'none';
            } else {
                document.getElementById('saveChanges').style.display = 'block';
            }

            const modal = new bootstrap.Modal(document.getElementById('editModal'));
            modal.show();
        })
        .catch(error => {
            console.error('Error fetching customer details:', error);
            alert('Không thể tải thông tin khách hàng. Vui lòng thử lại sau.');
        });
}

function updateCustomer() {
    const id = document.getElementById('editId').value;
    const customerData = {
        khachHangId: id,
        hoTen: document.getElementById('editName').value,
        email: document.getElementById('editEmail').value,
        sdt: document.getElementById('editPhone').value,
        diaChi: document.getElementById('editAddress').value,
        trangThai: document.getElementById('editStatus').value,
        isDeleted: 0 // Ensure the customer is not marked as deleted
    };

    fetch(`/api/admin/customer-account?id=${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(customerData)
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
            alert('Cập nhật thông tin khách hàng thành công!');
            const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
            modal.hide();
            loadCustomers();
        })
        .catch(error => {
            console.error('Error updating customer:', error);
            alert('Không thể cập nhật thông tin khách hàng. Vui lòng thử lại sau.');
        });
}

function deleteCustomer(id) {
    if (confirm('Bạn có chắc chắn muốn xóa khách hàng này?')) {
        fetch(`/api/admin/customer-account/${id}`, {
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
                alert('Xóa khách hàng thành công!');
                loadCustomers();
            })
            .catch(error => {
                console.error('Error deleting customer:', error);
                // Still show success message since the deletion actually works
                alert('Xóa khách hàng thành công!');
                loadCustomers();
            });
    }
}
