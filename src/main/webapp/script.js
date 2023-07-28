
function getParams() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams;
}


var wsocket;
function createNewMessage(obj) {
    var obj0 = JSON.parse(obj);
    var element = document.createElement("li");
    element.innerHTML = `<div class="entete">
                        <span class=${obj0.me ? "status green" : "status blue"}></span>
                        <h2>${obj0.sender}</h2>
                        <h3>${obj0.time}</h3>
                        </div>
                        <div class="triangle"></div>
                        <div class="message">
                            ${obj0.text}
                        </div>`
    element.setAttribute("class", obj0.me? "me" : "you");
    document.getElementById("chat").appendChild(element);

}

function createNewUser(obj) {
    var obj0 = JSON.parse(obj);
    if (!obj0.online) {
        document.getElementById(obj0.id).outerHTML = "";
        return;
    }
    var element = document.createElement("li");
    element.setAttribute("id", obj0.id);

    let imageElement = document.createElement("img");
    imageElement.setAttribute("width", 55);
    imageElement.setAttribute("length", 55);

    imageElement.setAttribute("src",obj0.aviURL);
    element.appendChild(imageElement);

    var divElement = document.createElement("div");
    let nameNode = document.createElement("h2");
    nameNode.innerText = obj0.name;
    divElement.appendChild(nameNode);

    let status = document.createElement("h3");

    let statusNode = document.createElement("span");
    statusNode.setAttribute("class", obj0.online ? "status green" : "status orange");
    status.appendChild(statusNode);

    let current = document.createTextNode(obj0.online ? "online" : "offline");
    status.appendChild(current);

    divElement.appendChild(status);


    element.appendChild(divElement);
    document.getElementById("aside").appendChild(element);
}

function connect() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    console.log("ws://localhost:9090/web_socket/chat"+window.location.search);
    wsocket = new WebSocket("ws://localhost:9090/web_socket/chat"+window.location.search);

/*    function onOpen() {
        alert("connected successfully");
    }

    wsocket.onopen=onOpen;
*/
    function onError(error, err) {
        alert("error happened " + err.type);
    }

    wsocket.onerror=onError;

    function onClose() {
        alert("closed successfully");
    }

    wsocket.onclose=onClose;

    function onMessage(evt) {
        const obj = JSON.parse(evt.data);
        if (obj.message != null)
            createNewMessage(obj.message);
        else if (obj.user){
            createNewUser(obj.user);
        }
        else
            alert(evt.data);
    }

    wsocket.onmessage=onMessage;
}
function send() {

    if (wsocket instanceof WebSocket)
    {
        var msg = document.getElementById("msg").value;
        wsocket.send(msg);
    }
    else {
        alert("connect first");
    }
}