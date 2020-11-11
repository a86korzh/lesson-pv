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
		<c:if test="${subject != null}">
			<form action="updateSubject" method="post">
        </c:if>
        <c:if test="${subject == null}">
			<form action="addSubject" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
            	<h2>
            		<c:if test="${subject != null}">
						Update Subject
            		</c:if>
            		<c:if test="${subject == null}">
            			Add New Subject
            		</c:if>
            	</h2>
            </caption>
        		<c:if test="${subject != null}">
        			<input type="hidden" name="id" value="<c:out value='${subject.id}' />" />
        		</c:if>            
            <tr>
                <th>Subject: </th>
                <td>
                	<input type="text" name="subject" size="30"
                			value="<c:out value='${subject.subjectName}' />"
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
