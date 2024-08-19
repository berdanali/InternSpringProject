const userId = localStorage.getItem('userId');

fetch(`/api/users/${userId}/tasks`)
    .then(response => response.json())
    .then(tasks => {
        const tasksDiv = document.getElementById('tasks');
        tasks.forEach(task => {
            const taskDiv = document.createElement('div');
            taskDiv.innerHTML = `
                <h2>${task.title}</h2>
                <p>${task.content}</p>
                <p>Start Date: ${task.startDate}</p>
                <p>End Date: ${task.endDate}</p>
                <button onclick="updateTask(${task.id})">Update</button>
            `;
            tasksDiv.appendChild(taskDiv);
        });
    });

function updateTask(taskId) {
    // Navigate to a page for updating the task
    window.location.href = `/updateTask.html?taskId=${taskId}`;
}
