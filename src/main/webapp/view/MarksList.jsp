<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>School Application</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Student Marks Management</h1>
    <h2>
        <a href="list">List All Students</a>
    </h2>
</div>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Student Marks</h2></caption>
        <tr>
            <th>Subject</th>
            <th>Marks</th>
        </tr>
        <c:forEach var="marks" items="${allMarks}" varStatus="loop">
            <tr>
                <td><c:out value="${marks.key.subjectName}" /></td>

                <td>
                    <c:forEach var="mark" items="${marks.value}">
                        <c:if test="${mark.mark>0}">
                            <c:out value="${mark.mark}, "/>
                        </c:if>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<br>
<div align="center">
    <form action = "addSubjectToStudent" method = "GET">
        <select name="subjectId" >
            <c:forEach items="${allSubjects}" var="subject">
                <option value="${subject.id}">
                        ${subject.subjectName}
                </option>
            </c:forEach>
        </select>
        <input type="hidden" name="studentId" value="<c:out value='${studentId}' />" />
        <input type = "submit" value = "Add Subject" />
    </form>
</div>
</body>
</html>
