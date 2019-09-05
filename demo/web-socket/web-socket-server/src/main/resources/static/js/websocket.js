/**
 *
 * @author caimb
 * @date Created in 2017/9/29 9:58
 * @modefier
 */
document.addEventListener("DOMContentLoaded", connect, false)
var socket;
function connect() {
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        if(socket == null){
            socket = new WebSocket("ws://localhost:8081/123");
        }
        socket.onmessage = function(event) {
            var ul = document.getElementById("chart_msg");
            var li = document.createElement("li");
            var div1 = document.createElement("div");
            var div2 = document.createElement("div");
            var span = document.createElement("span");
            span.style.paddingLeft="20px";
            span.style.marginLeft="20px";
            span.style.width="230px";
            span.style.overflow="hidden";
            span.style.marginTop="20px";
            span.innerHTML=event.data;
            var img = document.createElement("img");
            img.src="img/server.png";
            div2.style.backgroundColor="white";
            div2.style.minHeight="70px";
            div2.style.width="250px";
            div2.style.borderRadius="10px";
            div2.className="chart_text";
            div1.className="head_img";
            div1.appendChild(img);
            div2.appendChild(span);
            li.appendChild(div1);
            li.appendChild(div2);
            ul.appendChild(li);
        };
        socket.onopen = function(event) {
        };
        socket.onclose = function(event) {
            alert("连接已被关闭");
        };
    } else {
        alert("Your browser does not support Web Socket.");
    }
}
function sendContent() {
    var send_text = document.getElementById("send_text").value;
    var ul = document.getElementById("chart_msg");
    var li = document.createElement("li");
    var div1 = document.createElement("div");
    var div2 = document.createElement("div");
    var span = document.createElement("span");
    span.style.paddingLeft="20px";
    span.style.marginLeft="20px";
    span.style.width="230px";
    span.style.overflow="hidden";
    span.style.marginTop="20px";
    span.innerHTML=send_text;
    var img = document.createElement("img");
    img.src="img/psd.png";
    div2.style.backgroundColor="white";
    div2.style.minHeight="70px";
    div2.style.width="250px";
    div2.style.borderRadius="10px";
    div2.className="chart_text";
    div1.className="head_img";
    div1.appendChild(img);
    div2.appendChild(span);
    li.appendChild(div1);
    li.appendChild(div2);
    ul.appendChild(li);
    socket.send(send_text);
}
function sendFile() {
    var send_text = document.getElementById("file_input").value;
    var ul = document.getElementById("chart_msg");
    var li = document.createElement("li");
    var div1 = document.createElement("div");
    var div2 = document.createElement("div");
    var span = document.createElement("span");
    span.style.paddingLeft="20px";
    span.style.marginLeft="20px";
    span.style.width="230px";
    span.style.overflow="hidden";
    span.style.marginTop="20px";
    span.innerHTML=send_text;
    var img = document.createElement("img");
    img.src="img/psd.png";
    div2.style.backgroundColor="white";
    div2.style.minHeight="70px";
    div2.style.width="250px";
    div2.style.borderRadius="10px";
    div2.className="chart_text";
    div1.className="head_img";
    div1.appendChild(img);
    div2.appendChild(span);
    li.appendChild(div1);
    li.appendChild(div2);
    ul.appendChild(li);
    var inputElement = document.getElementById("file_input");
    var file = inputElement.files;
    if(file.length){
        for(var i=0;i<file.length;++i){

            var reader = new FileReader();
            //文件读取完毕后该函数响应
            reader.onload = function loaded(evt) {
                var binaryString = evt.target.result;
                //发送文件
                socket.send(binaryString);
            }
            //以二进制形式读取文件
            reader.readAsArrayBuffer(file[i]);
        }
    }
}