<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>School Application</title>
    <script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
        <script>
            $.ajax({
                url: "getStudentAmount",
                success: function(data){
                    document.getElementById("countOfStudents").innerHTML = data;
                }
            });
        </script>

        <table border="1" cellpadding="5">
            <caption><h2>List of Students</h2></caption>

            <caption><h3>Count of Students:  <b id="countOfStudents"></b></h3></caption>

            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Birthdate</th>
                <th>Enter year</th>
            </tr>

            <c:forEach var="student" items="${studentsList}">
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

    <br>

    <div align="center">
        <form action = "list" method = "GET">
            <label for="representedStudentsAmountId">Choose number of students for displaying:</label>
            <select name="representedStudentsAmount" id="representedStudentsAmountId" >
                <option value="2" ${representedStudentsAmount == 2 ? "selected" : ""}>2</option>
                <option value="3" ${representedStudentsAmount == 3 ? "selected" : ""}>3</option>
                <option value="5" ${representedStudentsAmount == 5 ? "selected" : ""}>5</option>
                <option value="10" ${representedStudentsAmount == 10 ? "selected" : ""}>10</option>
                <option value="20" ${representedStudentsAmount == 20 ? "selected" : ""}>20</option>
                <option value="50" ${representedStudentsAmount == 50 ? "selected" : ""}>50</option>
                <option value="100" ${representedStudentsAmount == 100 ? "selected" : ""}>100</option>
            </select>
        </form>
    </div>

    <br>

    <script>
        let studentsAmount = Number(${studentsAmount});
        let actualPageFirstRow = Number(${actualPageFirstRow});
        let representedStudentsAmount = Number($('#representedStudentsAmountId option:selected').val());
        let pagesAmount = Math.ceil(studentsAmount / representedStudentsAmount);

        $(document).ready(function(){
            $('#representedStudentsAmountId').change(function(){
                representedStudentsAmount = $('#representedStudentsAmountId option:selected').val();
                document.location.href = "list?representedStudentsAmount=" + representedStudentsAmount
                    + "&actualPageFirstRow=0";
            });
        });

        function getPage(representedStudentsAmountPar, actualPageFirstRowPar, pageNumberPar) {
            let a = document.createElement('a');
            a.href = "list?representedStudentsAmount=" + representedStudentsAmountPar
                + "&actualPageFirstRow=" + actualPageFirstRowPar;
            a.innerHTML = pageNumberPar;
            leftDiv.appendChild(a);

            let i = document.createElement('i');
            i.innerText = "      ";
            leftDiv.appendChild(i);
        }

        let leftDiv = document.createElement("div");
        leftDiv.setAttribute("style", "text-align: center");

        if (pagesAmount < 11) {
            for (let i = 0; i < pagesAmount; i++) {
                getPage(representedStudentsAmount, representedStudentsAmount * i, i + 1);
            }
        } else {
            if (actualPageFirstRow != 0 ){
                getPage(representedStudentsAmount, (actualPageFirstRow - representedStudentsAmount), 'previous');
            }

            getPage(representedStudentsAmount, 0, 1);

            let i = document.createElement('i');
            i.innerText = "...      ";
            leftDiv.appendChild(i);

            let input = document.createElement('input');
            input.id = 'currentPageId';
            input.size = 1;
            input.placeholder = (actualPageFirstRow / representedStudentsAmount + 1).toString();
            input.style = "text-align:center";

            leftDiv.appendChild(input);

            $(document).ready(function(){
                $('#currentPageId').keypress(function(e) {
                    if (e.which == 13) {
                        let currentPage = $('#currentPageId').val();

                        if (currentPage <= pagesAmount && currentPage >= 1){
                            $(location).attr('href','list?representedStudentsAmount=' + representedStudentsAmount
                                + '&actualPageFirstRow=' + representedStudentsAmount * (currentPage - 1));
                        } else {
                            alert('enter correct page');
                        }

                    }
                });
            });

            let i1 = document.createElement('i');
            i1.innerText = "      ...      ";
            leftDiv.appendChild(i1);

            getPage(representedStudentsAmount, representedStudentsAmount * (pagesAmount - 1), pagesAmount);

            if (actualPageFirstRow != (representedStudentsAmount * (pagesAmount - 1))) {
                getPage(representedStudentsAmount, (actualPageFirstRow + representedStudentsAmount), 'next');
            }
        }
        document.body.appendChild(leftDiv);
    </script>
</body>
</html>
