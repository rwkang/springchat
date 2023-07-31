const eventSource = new EventSource("http://localhost:8080/sender/ssal/receiver/cos");
// http://localhost:8080/sender/ssal/receiver/cos

eventSource.onmessage = (event) => {
    console.log("1 event: " + event);
    const data = JSON.parse(event.data);
    console.log("2 data: " + data);
    console.log("3 data.msg: " + data.msg);
}


// 보내는 메시지 펑션
// function getMessageBox(inputedMessage) {
getMessageBox = (inputedMessage) => {
    return `<div class="sent_msg">
              <p>${inputedMessage}</p>
              <span class="time_date"> 09:01    |    August 9</span> </div>
          </div>`
}

// function setSentMessageBox() {
setSentMessageBox = () => {
    // alert("보내기 아이콘 클릭으로 요청 됨!!");
    let chatBox = document.querySelector("#chat-box");

    // 2023.07.24 Conclusion. .value는 변수.value, 이런식으로 처리하는 것이, 더 효율적인이네.
    // let inputedMessage = document.querySelector("#chat-outgoing-message").value;
    let inputedMessage = document.querySelector("#chat-outgoing-message");
    console.log("inputedMessage.value: " + inputedMessage.value);
    if (inputedMessage.value === "") {
        return;
    }

    let chatOutgoingBox = document.createElement("div");

    chatOutgoingBox.className = "chat-outgoing-message";
    chatOutgoingBox.innerHTML = getMessageBox(inputedMessage.value);
    chatBox.append(chatOutgoingBox);
    inputedMessage.value = "";
}

// button click event
document.querySelector("#chat-outgoing-button").addEventListener("click", ()=> {
    setSentMessageBox();
})

// <input> tag Enter event
document.querySelector("#chat-outgoing-message").addEventListener("keydown", (e)=> {
    // alert("clicked!!");
    // console.log("e.keyCode: " + e.keyCode); // Enter key code: 13
    console.log("e.key: " + e.key); // Enter key: "Enter"
    if (e.keyCode === 13) {
        // alert("엔터 키로 요청 됨!!");
        setSentMessageBox();
    }
})
