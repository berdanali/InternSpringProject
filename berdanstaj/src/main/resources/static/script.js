$(document).ready(function () {
    $('#signup-form').on('submit', function (event) {
        event.preventDefault(); // Sayfanın yeniden yüklenmesini engeller

        var formData = {
            role: $('#role-select').val(),
            username: $('#username').val(),
            mail: $('#mail').val(),
            password: $('#password').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/users', // Sunucunuzdaki doğru URL
            data: JSON.stringify(formData),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (response) {
                toastr.success('Kullanıcı başarıyla eklendi!');
                setTimeout(function() {
                    window.location.href = 'admin.html';
                }, 1000); // 1000ms (1 saniye) sonra admin.html sayfasına yönlendirir
            },
            error: function (xhr, status, error) {
                toastr.error('Kullanıcı eklenirken bir hata oluştu!');
            }
        });
    });
});
