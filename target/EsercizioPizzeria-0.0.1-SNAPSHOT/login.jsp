<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <title>Login</title>
</head>
<body>

<h2>LOGIN</h2>

<form action="login" method="post">
    Username:<br>
    <input type="text" name="username"required><br><br>

    Password:<br>
    <input type="password" name="password"required><br><br>

    <input type="submit" value="LOGIN">
</form>

<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
    <p style="color:red;"><%= error %></p>
<%
    }
%>

</body>
</html>