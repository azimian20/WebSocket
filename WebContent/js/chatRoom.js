var CreateEngine = function(wsUri) {
	var websocket = null;
	var elements = null;

	var hideDirMsgPanel = function() {
		elements.dirMsgPanel.style.display = "none";
	};

	var displayMessage = function(msg) {
		if (elements.msgContainer.childNodes.length == 50) {
			elements.msgContainer
					.removeChild(elements.msgContainer.childNodes[0]);
		}
		var div = document.createElement('div');
		var textnode = document.createTextNode(msg.sender + ": " + msg.messageText);
		div.appendChild(textnode);
		elements.msgContainer.appendChild(div);
		elements.msgContainer.scrollTop = elements.msgContainer.scrollHeight;
	};

	var displayDirMessage = function(msg) {
		alert(msg.sender + ": " + msg.messageText);
	};

	var showDirMsgPanel = function(e) {
		elements.dirMsgPanel.style.display = "block";
		var receiver = e.getAttribute('data-title');
		elements.dirMsgReceiver.setAttribute('data-title', receiver);
		elements.dirMsgReceiver.innerHTML = receiver;
		elements.txtDirMsg.focus();
	};

	var displayOnlineUsers = function(onlineUsers) {
		elements.usrContainer.innerHTML = '';
		for (var i = 0; i < onlineUsers.length; i++) {
			var receiver = onlineUsers[i];
			if (receiver != elements.nickname) { // -Excluding myself from
													// the list of online users.
				var button = document.createElement('input');
				button.type = "button";
				button.className = "userSelector"
				button.value = receiver;
				button.setAttribute('data-title', receiver);
				elements.usrContainer.appendChild(button);
				button.addEventListener("click", function() {
					showDirMsgPanel(this);
				}, false);
			}
		}
	};

	var clearMessage = function() {
		elements.msgContainer.innerHTML = '';
	};

	var clearOnlineUsers = function() {
		elements.usrContainer.innerHTML = '';
	};

	return {
		initwebsocket : function(e) {
			elements = e;
			if (websocket == null) {
				websocket = new WebSocket(wsUri);
				websocket.onopen = function() {
				};
				websocket.onmessage = function(e) {
					var messageObj = JSON.parse(e.data);
					if (messageObj.messageType == "USERS_INFO") {
						var onlineUsers;// = new Array();
						onlineUsers = messageObj.onlineUsers;
						displayOnlineUsers(onlineUsers)
					} else if (messageObj.messageType == "PUBLIC_MESSAGE") {
						displayMessage(messageObj);
					} else if (messageObj.messageType == "DIRECT_MESSAGE") {
						displayDirMessage(messageObj); // unify this with abov
					}
				};
				websocket.onerror = function(e) {
				};
				websocket.onclose = function(e) {
					websocket = null;
					clearMessage();
					clearOnlineUsers();
				};
			}
		},
		sendMessage : function() {
			elements.txtMsg.focus();
			if (websocket != null && websocket.readyState == WebSocket.OPEN) {
				var input = elements.txtMsg.value.trim();
				if (input == '') {
					return;
				}
				elements.txtMsg.value = '';
				var message = {
					messageType : 'PUBLIC_MESSAGE',
					messageText : input
				};
				websocket.send(JSON.stringify(message));
			}
		},

		sendDirectMessage : function() {
			var receiver = elements.dirMsgReceiver.getAttribute('data-title');
			if (websocket != null && websocket.readyState == 1) {
				var input = elements.txtDirMsg.value.trim();
				if (input == '') {
					return;
				}
				elements.txtDirMsg.value = '';
				var message = {
					messageType : 'DIRECT_MESSAGE',
					messageText : input,
					receiver : receiver
				};
				websocket.send(JSON.stringify(message));
				hideDirMsgPanel();
			}
		},
		logout : function() {
			if (websocket != null && websocket.readyState == WebSocket.OPEN) {
				websocket.close();
			}
		}
	}
};