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
		<c:if test="${student != null}">
			<form action="updateStudent" method="post">
        </c:if>
        <c:if test="${student == null}">
			<form action="addStudent" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
            	<h2>
            		<c:if test="${student != null}">
						Update Student
            		</c:if>
            		<c:if test="${student == null}">
            			Add New Student
            		</c:if>
            	</h2>
            </caption>
        		<c:if test="${student != null}">
        			<input type="hidden" name="id" value="<c:out value='${student.id}' />" />
        		</c:if>
            <tr>
                <th>Name: </th>
                <td>
                	<input type="text" name="name" size="45"
                			value="<c:out value='${student.firstName}' />"
                		/>
                </td>
            </tr>
            <tr>
                <th>Surname: </th>
                <td>
                	<input type="text" name="surname" size="45"
                			value="<c:out value='${student.secondName}' />"
                	/>
                </td>
            </tr>
            <tr>
                <th>Birthdate: </th>
                <td>
                	<input type="text" name="birthdate" size="25"
                			value="<c:out value='${student.birthDate}' />"
                	/>
                </td>
            </tr>
			<tr>
				<th>Enter year: </th>
				<td>
					<input type="text" name="enterYear" size="15"
						   value="<c:out value='${student.enterYear}' />"
					/>
				</td>
			</tr>
            <tr>
            	<td colspan="2" align="center">
            		<input type="submit" value="Save" />
            	</td>
            </tr>
        </table>
        </form>
    </div>	
</body>
</html>
