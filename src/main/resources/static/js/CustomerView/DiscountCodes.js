function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(function () {
        alert('Đã sao chép mã giảm giá: ' + text);
    }, function (err) {
        console.error('Không thể sao chép: ', err);
    });
}