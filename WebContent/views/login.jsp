<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="style/style.css">
<title>Login</title>
</head>


<body>
<div id="mainDiv">
<div class="title">
	Simple Chat App! 
</div>

<c:if test="${not empty errormsg}">
<div class="errorMessage">
	 <c:out value="${errormsg}"></c:out>
</div>
	</c:if>

		<form action="<c:url value='/login'/>" method="POST">
			nickname <input type="text" name="nickName" class="input"> 
			<input type="submit" value="login" class="button button-high">
		</form>
        </div>
</body>





</html>