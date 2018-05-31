<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Simple Chat Application</title>
<script type="text/javascript" src="js/chatRoom.js" charset="UTF-8"></script>
<link rel="stylesheet" href="style/style.css">
</head>

<body>
<script type="text/javascript">
	var engine = CreateEngine("${chat_socket_uri}");
	function startChatroom(){
		engine.initwebsocket({
			msgPanel : document.getElementById('msgPanel'),
			dirMsgPanel : document.getElementById('dirMsgPanel'),
			dirMsgReceiver : document.getElementById('dirMsgReceiver'),
			txtMsg : document.getElementById('txtMsg'),
			txtDirMsg : document.getElementById('txtDirMsg'),
			msgContainer : document.getElementById('msgContainer'),
			usrContainer : document.getElementById('usrContainer'),
			nickname : "${nickName}" //-The nickname of this very logged-in user.
		});
		resetTimer();
	}
	window.onload = function(){
		startChatroom();
	}
	var timekeeper;
	function starttimer() {
		timekeeper = setTimeout("logout()", ('${session_timout}' * 1000));
	}
	function resetTimer() {
	    clearTimeout(timekeeper);
	    starttimer();
	}
	
	function sendMessage(){
		engine.sendMessage();
		resetTimer();
	}
	function sendDirectMessage(){
		engine.sendDirectMessage();
		resetTimer();
	}
	function logout(){
		engine.logout();
		document.getElementById("logout").submit();
	}
	window.onunload = function(){
		logout();
		return "";
	};
	
</script>


<div id="mainDiv">
<div class="title">
	${nickName}
</div>

	<div id="msgPanel" style="display: block">
		<div id="msgContainer" class="msgRoom" ></div>
		<div id="msgController">
			<textarea id="txtMsg" title="Enter to send message" class="msgBox"
				class="msgBox" rows="2"></textarea>
			<button type="button" class="button button-high"
				onclick="sendMessage()">send</button>
			<button type="button" class="button"
				onclick="logout()">logout</button>
		</div>
	</div>
	<hr>
	<div id="usrsPanel" style="display: block">
	<div class="title">
		Online Users:
	</div>
		<div id="usrContainer" style="overflow: auto;">
		<!-- list of online users are shown here -->
		</div>
	</div>
	<div id="dirMsgPanel" style="display: none">
	<div id="dirMsgReceiver" data-title="receiver"></div>
		<div id="dirMsgController">
			<textarea id="txtDirMsg" title="Enter to send direct message"
				class="msgBox" rows="3"></textarea>
			<button type="button" class="button button-high"
				onclick="sendDirectMessage()">send</button>
		</div>
	</div>
	<form id="logout" action="<c:url value='/logout'/>" method="POST"></form>
	</div>
</body>
</html>