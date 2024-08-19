document.addEventListener('DOMContentLoaded', function() {
    var role = localStorage.getItem('role');
    var fullname = localStorage.getItem('fullname');
    var dashboardTitle = document.getElementById('dashboard-title');
    var fullnameElement = document.getElementById('fullname');

    // Kullanıcı adını sağ üstte göster
    if (fullname) {
        fullnameElement.textContent = fullname;
    }

    // Role göre dashboard başlığını güncelle
    switch (role) {
        case 'İdari Personel':
            dashboardTitle.textContent = 'İdari Personel Dashboard';
            break;
        case 'Akademisyen':
            dashboardTitle.textContent = 'Akademisyen Dashboard';
            break;
        case 'Teknik Ekip':
            dashboardTitle.textContent = 'Teknik Ekip Dashboard';
            break;
        case 'Yönetici':
            dashboardTitle.textContent = 'Yönetici Dashboard';
            break;
        default:
            dashboardTitle.textContent = 'Dashboard';
    }
});

document.getElementById('logoutButton').addEventListener('click', function() {
    localStorage.removeItem('fullname');
    localStorage.removeItem('role');

    toastr.options = {
        "positionClass": "toast-top-right",
        "timeOut": "1000"
    };
    toastr.success('Logout successful');

    setTimeout(() => {
        window.location.href = '/login.html';
    }, 1000);
});
