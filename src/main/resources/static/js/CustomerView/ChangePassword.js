document.getElementById('passwordChangeForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    // Hide any existing alerts
    document.getElementById('successAlert').style.display = 'none';
    document.getElementById('errorAlert').style.display = 'none';
    
    // Get form values
    const userId = document.getElementById('userId').value;
    const currentPassword = document.getElementById('currentPassword').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    
    // Basic validation
    if (newPassword !== confirmPassword) {
        document.getElementById('errorAlert').textContent = 'Mật khẩu mới và xác nhận mật khẩu không khớp';
        document.getElementById('errorAlert').style.display = 'block';
        return;
    }
    
    if (newPassword.length < 8) {
        document.getElementById('errorAlert').textContent = 'Mật khẩu mới phải có ít nhất 8 ký tự';
        document.getElementById('errorAlert').style.display = 'block';
        return;
    }
    
    try {
        const response = await fetch('/api/change-password', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userId: userId,
                currentPassword: currentPassword,
                newPassword: newPassword,
                confirmPassword: confirmPassword
            })
        });
        
        const data = await response.json();
        
        if (response.ok) {
            // Success
            document.getElementById('successAlert').textContent = data.message;
            document.getElementById('successAlert').style.display = 'block';
            
            // Reset form
            document.getElementById('currentPassword').value = '';
            document.getElementById('newPassword').value = '';
            document.getElementById('confirmPassword').value = '';
        } else {
            // Error
            document.getElementById('errorAlert').textContent = data.message;
            document.getElementById('errorAlert').style.display = 'block';
        }
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('errorAlert').textContent = 'Có lỗi xảy ra khi kết nối đến máy chủ';
        document.getElementById('errorAlert').style.display = 'block';
    }
});
