document.addEventListener('DOMContentLoaded', function () {
    loadPendingPartners();

    // Xử lý tìm kiếm
    document.getElementById('searchBtn').addEventListener('click', function () {
        loadPendingPartners(document.getElementById('searchInput').value);
    });
});

function loadPendingPartners(searchTerm = '') {
    fetch('/api/admin/partner-account/approval')
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
                    <td>${partner.diaChi || '-'}</td>
                    <td>${partner.TKNH || '-'}</td>
                    <td><span class="badge bg-warning">Chờ phê duyệt</span></td>
                    <td>
                        <button class="btn btn-sm btn-info" onclick="viewPartner(${partner.doiTacId})">
                            <i class="bi bi-eye"></i> Xem
                        </button>
                        <button class="btn btn-sm btn-success" onclick="approvePartner(${partner.doiTacId})">
                            <i class="bi bi-check-circle"></i> Phê duyệt
                        </button>
                        <button class="btn btn-sm btn-danger" onclick="rejectPartner(${partner.doiTacId})">
                            <i class="bi bi-x-circle"></i> Từ chối
                        </button>
                    </td>
                `;
                tableBody.appendChild(row);
            });

            if (filteredData.length === 0) {
                tableBody.innerHTML = `
                    <tr>
                        <td colspan="8" class="text-center">Không có đối tác nào đang chờ phê duyệt</td>
                    </tr>
                `;
            }
        })
        .catch(error => {
            console.error('Error loading partners:', error);
            alert('Không thể tải dữ liệu đối tác. Vui lòng thử lại sau.');
        });
}

function viewPartner(id) {
    fetch(`/api/admin/partner-account/${id}`)
        .then(response => response.json())
        .then(partner => {
            document.getElementById('viewName').textContent = partner.hoTen;
            document.getElementById('viewEmail').textContent = partner.email;
            document.getElementById('viewPhone').textContent = partner.sdt || '-';
            document.getElementById('viewCccd').textContent = partner.cccd || '-';
            document.getElementById('viewAddress').textContent = partner.diaChi || '-';
            document.getElementById('viewBankAccount').textContent = partner.TKNH || '-';
            document.getElementById('viewBankAccountName').textContent = partner.tenTKNH || '-';
            document.getElementById('viewBankName').textContent = partner.tenNH || '-';

            // Set up approve and reject buttons
            document.getElementById('approveBtn').onclick = function () {
                approvePartner(id);
                const modal = bootstrap.Modal.getInstance(document.getElementById('viewModal'));
                modal.hide();
            };

            document.getElementById('rejectBtn').onclick = function () {
                rejectPartner(id);
                const modal = bootstrap.Modal.getInstance(document.getElementById('viewModal'));
                modal.hide();
            };

            const modal = new bootstrap.Modal(document.getElementById('viewModal'));
            modal.show();
        })
        .catch(error => {
            console.error('Error fetching partner details:', error);
            alert('Không thể tải thông tin đối tác. Vui lòng thử lại sau.');
        });
}

function approvePartner(id) {
    if (confirm('Bạn có chắc chắn muốn phê duyệt đối tác này?')) {
        fetch(`/api/admin/partner-account/approve/${id}`, {
            method: 'PUT'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(data => {
                alert('Phê duyệt đối tác thành công!');
                loadPendingPartners();
            })
            .catch(error => {
                console.error('Error approving partner:', error);
                alert('Không thể phê duyệt đối tác. Vui lòng thử lại sau.');
            });
    }
}

function rejectPartner(id) {
    if (confirm('Bạn có chắc chắn muốn từ chối đối tác này?')) {
        fetch(`/api/admin/partner-account/reject/${id}`, {
            method: 'PUT'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(data => {
                alert('Từ chối đối tác thành công!');
                loadPendingPartners();
            })
            .catch(error => {
                console.error('Error rejecting partner:', error);
                alert('Không thể từ chối đối tác. Vui lòng thử lại sau.');
            });
    }
}
