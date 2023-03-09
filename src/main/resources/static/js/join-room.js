let socket;
let stompClient;
let roomId;
let chatBox;
let messageField;
let btnSend;
let messageForm;
let username;

$(document).ready(function () {
    roomId = $("#roomId").text();
    chatBox = $("#chatBox");
    messageField = $("#messageField");
    btnSend = $("#btnSend");
    messageForm = $("#messageForm");
    username = $("#username");

    console.log("Room ID: " + roomId);
    connect();
    messageForm.on("submit", function (e) {
        e.preventDefault();
        sendMessage();
    });
});
function connect() {
    console.log("Connecting to websocket....")
    socket = new SockJS("/ws")
    stompClient = Stomp.over(socket);
    stompClient.connect(
        {'roomId': roomId},
        stompSuccess,
        stompFailure
    );
}

function stompSuccess() {
    console.log("Successfully connect to websocket");
    stompClient.subscribe("/room/connected.users", updateConnectedUsers());
    stompClient.subscribe('/room/old.messages', oldMessages);
    stompClient.subscribe('/topic/' + roomId + '.public.messages', publicMessages);
}

function stompFailure() {
    console.log("Failed to connect to websocket");
}

function updateConnectedUsers(response) {
    console.log("Getting connected user successfully");
    // let connectedUsers = JSON.parse(response.body);
    // console.log(connectedUsers);
}

function publicMessages(message) {
    console.log("Handling public message");
    let publicMessage = JSON.parse(message.body);
    appendMessage(publicMessage);
}

function appendMessage(message) {
    if(message.fromUser == "admin") {
        appendPublicMessage(message);
        return;
    }
    let usernameValue = username.text();
    let formattedTime = $.format.date(message.dateTime, 'dd/MM/yyyy HH:mm');
    if(message.fromUser == usernameValue) {
        chatBox.append(
            `<div class="d-flex">
                            <!--Message from other-->
                            <div class="w-50 text-start mb-2"></div>
                            <!--Message from me-->
                            <div class="w-50 text-end mb-3">
                                <div class="d-inline-block border rounded-3 px-3 py-1">
                                    <div>
                                        <!--Sender name-->
                                        <div class="text-start text-primary mb-1 fs-6 fw-bold">${message.fromUser}</div>
                                        <!--Chat content-->
                                        <p class="mb-1 text-start">${message.content}</p>
                                        <!--Chat time-->
                                        <small class="mb-1">${formattedTime}</small>
                                    </div>
                                </div>
                            </div>
                        </div>`
        );
    } else {
        chatBox.append(
            `<div class="d-flex">
                            <!--Message from other-->
                            <div class="w-50 text-start mb-2">
                                <div class="d-inline-block border rounded-3 px-3 py-1">
                                    <div>
                                        <!--Sender name-->
                                        <div class="text-start text-primary mb-1 fs-6 fw-bold">${message.fromUser}</div>
                                        <!--Chat content-->
                                        <p class="mb-1 text-start">${message.content}</p>
                                        <!--Chat time-->
                                        <p class="mb-1">${formattedTime}</p>
                                    </div>
                                </div>
                            </div>

                            <!--Message from me-->
                            <div class="w-50 text-end mb-2"></div>
                        </div>`
        )
    }
    scrollDownMessagesPanel();
}
function appendPublicMessage(publicMessage) {
    chatBox.append(
        '<div class="m-2 text-center"><h6 className="alert alert-success"><strong>System: </strong>'+publicMessage.content+'</h6></div>'
    );
    scrollDownMessagesPanel();
}
function oldMessages(response) {
    console.log("Handling old message");
    let messages = JSON.parse(response.body);
    $.each(messages, function (index, message) {
        appendMessage(message);
    });
}

function sendMessage() {
    if(messageField.val().trim().length === 0 ) {
        messageField.focus();
        return;
    }

    let messageObject = {
        'username': null,
        'roomId': null,
        'dateTime': null,
        'fromUser': null,
        'content': messageField.val(),
        'toUser': null
    };
    stompClient.send('/room/send.message',{}, JSON.stringify(messageObject));
    messageField.val('').focus();

}

function emptyInputMessage() {
    messageField.val("");
}

function scrollDownMessagesPanel() {
    chatBox.animate({"scrollTop": chatBox[0].scrollHeight}, "fast");
}