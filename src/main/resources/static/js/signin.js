document.getElementById('loginForm').addEventListener('submit', async function(event) {
    event.preventDefault();
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    
    try {
        let response = await fetch('http://localhost:8080/api/signin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username: username, password: password })
        });
        
        if (response.ok) {
            let data = await response.json();
            document.cookie = `token=${data.token}; path=/; HttpOnly`;
            window.location.href = 'http://localhost:8080/role/' + username;
        } else {
            // Handle error responses
            const errorText = await response.text();
            console.error('Login failed:', response.status, errorText);
            alert('Login failed: ' + (response.status === 401 ? 'Invalid credentials' : 'Server error'));
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Could not connect to server');
    }
});