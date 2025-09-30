let ws;
let userName = '';
const chatHistory = document.getElementById('chat-history');
const messageInput = document.getElementById('message-input');
const chatForm = document.getElementById('chat-form');
const nameInput = document.getElementById('name-input');
const joinButton = document.getElementById('join-button');
const userNameDisplay = document.getElementById('user-name');

window.onload = () => {
    const nameModal = new bootstrap.Modal(document.getElementById('nameModal'));
    nameModal.show();
};

function joinChat() {
    userName = nameInput.value.trim();
    if (!userName) return;
    
    ws = new WebSocket("ws://localhost:6082/chat");
    
    ws.onopen = () => {
        const nameModal = bootstrap.Modal.getInstance(document.getElementById('nameModal'));
        nameModal.hide();
        userNameDisplay.textContent = `Usuário: ${userName}`;

        ws.send(`sistema: ${userName} entrou no chat`);
        messageInput.focus();
    };

    ws.onmessage = (evt) => {
        const message = evt.data;
        
        if (message.startsWith('sistema:')) {
            addSystemMessage(message);
        } else {
            addReceivedMessage(message);
        }
    };

    ws.onerror = () => {
        addSystemMessage("sistema: Erro de conexão");
    };
    
    ws.onclose = () => {
        addSystemMessage("sistema: Conexão fechada");
    };
}

function addSystemMessage(message) {
    const messageReplace = message.replace("sistema: ", "");
    const div = document.createElement('div');
    div.className = 'chat-bubble system-message';
    div.innerHTML = `<div>${messageReplace}</div>`;
    chatHistory.appendChild(div);
    scrollToBottom();
}

function addMyMessage(message) {
    const div = document.createElement('div');
    div.className = 'chat-bubble sent-message';
    div.innerHTML = `<div class="message-sender">Você</div><div>${message}</div>`;
    chatHistory.appendChild(div);
    scrollToBottom();
}

function addReceivedMessage(message) {
    const div = document.createElement('div');
    div.className = 'chat-bubble received-message';
    
    if (message.includes(': ')) {
        const [sender, ...messageParts] = message.split(': ');
        const messageText = messageParts.join(': ');
        div.innerHTML = `<div class="message-sender">${sender}</div><div>${messageText}</div>`;
    } else {
        div.innerHTML = `<div>${message}</div>`;
    }
    
    chatHistory.appendChild(div);
    scrollToBottom();
}

function sendMessage() {
    const message = messageInput.value.trim();
    if (message && ws && ws.readyState === WebSocket.OPEN) {
        addMyMessage(message);
        ws.send(`${userName}: ${message}`);
        messageInput.value = '';
    }
}

function scrollToBottom() {
    chatHistory.scrollTop = chatHistory.scrollHeight;
}

joinButton.onclick = joinChat;
nameInput.onkeypress = (e) => {
    if (e.key === 'Enter') joinChat();
};

chatForm.onsubmit = (e) => {
    e.preventDefault();
    sendMessage();
};