document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault();

    var email = document.getElementById('mail').value;
    var password = document.getElementById('password').value;

    fetch('/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ mail: email, password: password })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        if (data.redirectUrl) {
            localStorage.setItem('fullname', data.fullname);
            localStorage.setItem('role', data.role);
            window.location.href = data.redirectUrl;
        } else {
            toastr.error(data);
        }
    })
    .catch(error => {
        console.error('There has been a problem with your fetch operation:', error);
        toastr.error('Login failed: ' + error.message);
    });
});
