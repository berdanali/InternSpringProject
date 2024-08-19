document.getElementById('addUserForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var fullname = document.getElementById('fullname').value;
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;
    var role = document.getElementById('role').value;

    fetch('/api/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ fullname: fullname, mail: email, password: password, role: role })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        if (data) {
            toastr.success('User added successfully'); // Toastr mesajı
            setTimeout(() => {
                window.location.href = '/admin.html'; // Yönlendirme
            }, 1000);
        } else {
            toastr.error('Failed to add user');
        }
    })
    .catch(error => {
        console.error('There has been a problem with your fetch operation:', error);
        toastr.error('Failed to add user: ' + error.message);
    });
});
