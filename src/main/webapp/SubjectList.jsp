<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>School Application</title>
</head>
<body>
	<div style="text-align: center;">
		<h1>Subject Management</h1>
        <h2>
        	<a href="newStudent">Add New Student</a>
        	&nbsp;
        	<a href="list">List All Students</a>
            <br>
            <a href="newSubject">Add New Subject</a>
            &nbsp;
            <a href="allSubjects">List All Subjects</a>
        </h2>
	</div>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Subjects</h2></caption>
            <tr>
                <th>ID</th>
                <th>Subject</th>
            </tr>
            <c:forEach var="subject" items="${allSubjects}">
                <tr>
                    <td><c:out value="${subject.id}" /></td>
                    <td><c:out value="${subject.subjectName}" /></td>
                    <td>
                    	<a href="editSubject?id=<c:out value='${subject.id}' />">Update</a>
                    	&nbsp;
                    	<a href="deleteSubject?id=<c:out value='${subject.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>	
</body>
</html>
