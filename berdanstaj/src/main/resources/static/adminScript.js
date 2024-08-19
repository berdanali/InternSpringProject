document.addEventListener('DOMContentLoaded', function() {
    // Kullanıcı adı ve diğer bilgiler
    var fullname = localStorage.getItem('fullname');
    var fullnameElement = document.getElementById('fullname');

    if (fullname) {
        fullnameElement.textContent = fullname;
    } else {
        fullnameElement.textContent = 'Guest';
    }

    // Kullanıcıları yükleme fonksiyonu
    function loadUsers() {
        fetch('/api/getAll')
            .then(response => response.json())
            .then(data => {
                const tbody = document.querySelector('tbody');
                tbody.innerHTML = ''; // Mevcut içeriği temizle

                data.forEach(user => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${user.id}</td>
                        <td>${user.fullname}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                        <td>${user.createdDate}</td>
                        <td>${user.updatedDate}</td>
                        <td>
                            <a class="btn btn-sm btn-info" href="javascript:viewUser(${user.id})">View</a>
                            <a class="btn btn-sm btn-warning" href="javascript:editUser(${user.id})">Edit</a>
                            <a class="btn btn-sm btn-danger" href="javascript:deleteUser(${user.id})">Delete</a>
                        </td>
                    `;
                    tbody.appendChild(row);
                });
            })
            .catch(error => console.error('Error fetching users:', error));
    }

    // İlk yüklemede kullanıcıları getir
    loadUsers();

    // Kullanıcıyı silme işlemi
    window.deleteUser = function(userId) {
        if (confirm('Are you sure you want to delete this user?')) {
            fetch(`/api/remove/${userId}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.ok) {
                    toastr.success('User removed successfully');
                    loadUsers(); // Tabloda değişiklikleri yansıtmak için kullanıcıları tekrar yükle
                } else {
                    toastr.error('Failed to remove user');
                }
            })
            .catch(error => console.error('Error deleting user:', error));
        }
    };

    // Logout işlemi
    document.getElementById('logoutButton').addEventListener('click', function() {
        localStorage.removeItem('fullname');

        toastr.options = {
            "positionClass": "toast-top-right",
            "timeOut": "1000"
        };
        toastr.success('Logout successful');

        setTimeout(() => {
            window.location.href = '/login.html';
        }, 1000);
    });

    // Kullanıcı ekleme işlemi
    document.getElementById('saveButton').addEventListener('click', function() {
        const fullname = document.getElementById('fullnameInput').value;
        const email = document.getElementById('emailInput').value;
        const role = document.getElementById('roleInput').value;

        fetch('/api/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ fullname, email, role })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                toastr.success('User added successfully');
                setTimeout(() => {
                    window.location.href = '/admin';
                }, 1000);
            } else {
                toastr.error('Failed to add user');
            }
        })
        .catch(error => console.error('Error adding user:', error));
    });
});

// Kullanıcı düzenleme işlemi
function editUser(userId) {
    window.location.href = `/edit.html?id=${userId}`;
}

// Kullanıcı görüntüleme işlemi
function viewUser(userId) {
    window.location.href = `/view.html?id=${userId}`;
}

// Kullanıcı güncelleme işlemi
function updateUser(userId, updatedUser) {
    fetch(`/api/edit/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedUser)
    })
    .then(response => {
        if (response.ok) {
            toastr.success('Edit successfully');
            setTimeout(() => {
                window.location.href = '/admin.html';
            }, 1000);
        } else {
            toastr.error('Failed to update user');
        }
    })
    .catch(error => console.error('Error updating user:', error));
}
function redirectToTaskPage(userId) {
    window.location.href = `/task.html?userId=${userId}`;
}
