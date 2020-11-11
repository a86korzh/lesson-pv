<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>School Application</title>
</head>
<body>
	<div style="text-align: center;">
		<h1>Student Management</h1>
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
            <caption><h2>List of Students</h2></caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Birthdate</th>
                <th>Enter year</th>
            </tr>
            <c:forEach var="student" items="${allStudents}">
                <tr>
                    <td><c:out value="${student.id}" /></td>
                    <td><c:out value="${student.firstName}" /></td>
                    <td><c:out value="${student.secondName}" /></td>
                    <td><c:out value="${student.birthDate}" /></td>
                    <td><c:out value="${student.enterYear}" /></td>
                    <td>
                    	<a href="editStudent?id=<c:out value='${student.id}' />">Update</a>
                    	&nbsp;
                    	<a href="deleteStudent?id=<c:out value='${student.id}' />">Delete</a>
                        &nbsp;
                        <a href="getStudentMarksById?id=<c:out value='${student.id}' />">Show marks</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>	
</body>
</html>
