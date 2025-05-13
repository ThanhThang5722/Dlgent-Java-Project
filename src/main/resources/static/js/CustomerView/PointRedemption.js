function quyDoiDiem(maGiamId) {
    if (confirm('Bạn có chắc chắn muốn quy đổi điểm để lấy mã giảm giá này?')) {
        fetch(`/api/quy-doi/${maGiamId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ userId: 1 }) // Mặc định userId là 1
        })
            .then(response => {
                if (response.ok) {
                    alert('Quy đổi điểm thành công!');
                    window.location.reload();
                } else {
                    return response.text().then(text => {
                        throw new Error(text || 'Có lỗi xảy ra khi quy đổi điểm');
                    });
                }
            })
            .catch(error => {
                alert(error.message);
                console.error('Lỗi:', error);
            });
    }
}