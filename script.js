const loginForm = document.getElementById('loginForm');
const mainSection = document.getElementById('main');
const projectSection = document.getElementById('projectSection');
const todoSection = document.getElementById('todoSection');
const projectList = document.getElementById('projectList');
const todoList = document.getElementById('todoList');
const newProjectBtn = document.getElementById('newProjectBtn');
const backToProjectsBtn = document.getElementById('back-to-projects-btn'); // Updated button ID
const addTodoBtn = document.getElementById('add-todo-btn'); // Added button ID
const newTodoDescription = document.getElementById('new-todo-description'); // Added input field ID
const projectTitle = document.getElementById('projectTitle');
const exportBtn = document.getElementById('export-gist-btn'); // Updated button ID
let username = '';
let password = '';

loginForm.addEventListener('submit', (e) => {
    e.preventDefault();
    username = document.getElementById('username').value;
    password = document.getElementById('password').value;
    login();
});

newProjectBtn.addEventListener('click', createProject);
backToProjectsBtn.addEventListener('click', () => {
    todoSection.classList.add('hidden');
    projectSection.classList.remove('hidden');
});

addTodoBtn.addEventListener('click', () => {
    const description = newTodoDescription.value;
    const projectId = todoSection.getAttribute('data-project-id');
    createTodo(projectId, description);
});

exportBtn.addEventListener('click', () => {
    const projectId = todoSection.getAttribute('data-project-id');
    exportProjectSummary(projectId, projectTitle.textContent);
});

function login() {
    document.getElementById('login').classList.add('hidden');
    mainSection.classList.remove('hidden');
    fetchProjects();
}

function fetchProjects() {
    fetch('http://localhost:8080/api/projects', {
        headers: {
            'Authorization': 'Basic ' + btoa(username + ':' + password)
        }
    })
    .then(response => response.json())
    .then(data => {
        projectList.innerHTML = '';
        data.forEach(project => {
            const li = document.createElement('li');
            li.textContent = project.title;
            li.addEventListener('click', () => fetchTodos(project.id, project.title));
            projectList.appendChild(li);
        });
    });
}

function fetchTodos(projectId, title) {
    fetch(`http://localhost:8080/api/todos/${projectId}`, {
        headers: {
            'Authorization': 'Basic ' + btoa(username + ':' + password)
        }
    })
    .then(response => response.json())
    .then(data => {
        todoList.innerHTML = '';
        data.forEach(todo => {
            const li = document.createElement('li');
            li.textContent = `${todo.description} (${todo.status ? 'Complete' : 'Pending'})`;

            const buttonGroup = document.createElement('div');
            buttonGroup.classList.add('button-group');

            // Add update button
            const updateBtn = document.createElement('button');
            updateBtn.textContent = 'Update';
            updateBtn.classList.add('update-btn');
            updateBtn.addEventListener('click', () => updateTodoStatus(todo.id, !todo.status));
            buttonGroup.appendChild(updateBtn);

            // Add delete button
            const deleteBtn = document.createElement('button');
            deleteBtn.textContent = 'Delete';
            deleteBtn.classList.add('delete-btn');
            deleteBtn.addEventListener('click', () => deleteTodo(todo.id, projectId));
            buttonGroup.appendChild(deleteBtn);

            li.appendChild(buttonGroup);
            todoList.appendChild(li);
        });
        projectTitle.textContent = title;
        todoSection.setAttribute('data-project-id', projectId);
        todoSection.classList.remove('hidden');
        projectSection.classList.add('hidden');
    });
}

function createProject() {
    const title = prompt('Enter project title:');
    if (title) {
        fetch('http://localhost:8080/api/projects', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa(username + ':' + password)
            },
            body: JSON.stringify({ title })
        })
        .then(response => response.json())
        .then(() => fetchProjects());
    }
}

function createTodo(projectId, description) {
    fetch(`http://localhost:8080/api/todos/${projectId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa(username + ':' + password)
        },
        body: JSON.stringify({ description })
    })
    .then(response => response.json())
    .then(() => fetchTodos(projectId, projectTitle.textContent));
}

function updateTodoStatus(todoId, status) {
    fetch(`http://localhost:8080/api/todos/${todoId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa(username + ':' + password)
        },
        body: JSON.stringify({ status })
    })
    .then(response => response.json())
    .then(data => fetchTodos(data.projectId, projectTitle.textContent));
}

function deleteTodo(todoId, projectId) {
    fetch(`http://localhost:8080/api/todos/${todoId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': 'Basic ' + btoa(username + ':' + password)
        }
    })
    .then(() => fetchTodos(projectId, projectTitle.textContent));
}

function exportProjectSummary(projectId, title) {
    fetch(`http://localhost:8080/api/todos/${projectId}`, {
        headers: {
            'Authorization': 'Basic ' + btoa(username + ':' + password)
        }
    })
    .then(response => response.json())
    .then(data => {
        const pendingTodos = data.filter(todo => !todo.status);
        const completedTodos = data.filter(todo => todo.status);

        exportFile(title, new Date().toISOString().split('T')[0], pendingTodos, completedTodos);
    });
}

const exportFile = (project_title, created_date, pending_todo_list, completed_todo_list) => {
    const content = 
    `
# ${project_title}
##### ${created_date}
___

### Summary : ${completed_todo_list.length}/${completed_todo_list.length + pending_todo_list.length} completed.

## Pending
${pending_todo_list.map((item) => `- [ ] ${item.description}`).join('\n')}

## Completed
${completed_todo_list.map((item) => `- [x] ${item.description}`).join('\n')}
    `;

    const element = document.createElement('a');
    const file = new Blob([content], { type: "text/markdown" });
    element.href = URL.createObjectURL(file);
    element.download = `${project_title}.md`;
    element.click();
}
