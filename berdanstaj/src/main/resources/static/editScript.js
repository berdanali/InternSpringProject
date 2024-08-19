document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const userId = urlParams.get('id');

    if (userId) {
        fetch(`/api/users/${userId}`)
            .then(response => response.json())
            .then(user => {
                document.getElementById('fullname').value = user.fullname;
                document.getElementById('email').value = user.mail;
                document.getElementById('role').value = user.role;
            })
            .catch(error => console.error('Error fetching user data:', error));
    }

    document.getElementById('editUserForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const updatedUser = {
            fullname: document.getElementById('fullname').value,
            mail: document.getElementById('email').value,
            role: document.getElementById('role').value
        };

        fetch(`/api/edit/${userId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedUser)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            toastr.success('Edit Successful');

            setTimeout(() => {
                window.location.href = 'admin.html';
            }, 1000);
        })
        .catch(error => {
            console.error('Error updating user:', error);
            toastr.error('Edit failed: ' + error.message);
        });
    });
});
