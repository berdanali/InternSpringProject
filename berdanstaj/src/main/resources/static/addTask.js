// Extract userId from URL
const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get('userId');
document.getElementById('userId').value = userId;

document.getElementById('task-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const taskData = {
        user: {
            id: document.getElementById('userId').value
        },
        title: document.getElementById('title').value,
        content: document.getElementById('content').value,
        startDate: document.getElementById('startDate').value,
        endDate: document.getElementById('endDate').value
    };

    fetch('/api/tasks', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(taskData)
    })
    .then(response => response.json())
    .then(data => {
        alert('Task update successful');
        setTimeout(() => {
            window.location.href = '/admin.html';
        }, 1000);
    })
    .catch(error => {
        alert('Error: ' + error);
    });
});
