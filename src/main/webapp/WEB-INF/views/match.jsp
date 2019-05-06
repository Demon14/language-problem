<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Language Application</title>
</head>
<body>
<h1>Language Application</h1>
<hr>

<h2>Your text matches the following languages :</h2>

<table class="table">
    <thead>
    <tr>
        <th>Language</th>
        <th>Accuracy</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${selections}">
        <tr>
            <td>${item.language}</td>
            <td> - ${item.accuracy}%</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>