<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<html>
<head>
<title>Error</title>
</head>
<body>
	<div style="text-align: center;">
		<h1>Error</h1>
		<h2><%=exception.getMessage() %><br/> </h2>
		<hr/>
		<a href="list">List All Students</a>
	</div>
</body>
</html>