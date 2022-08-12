<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 31.07.2022
  Time: 12:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Товарные операции</title>
    <style>
        <%@ include file="css/style.css"%>
    </style>
</head>
<body>
<h2>Вид товарной операции:</h2>
<table border="1" cellspacing="2" , cellpadding="10">
    <tr>
        <th>Инд. номер</th>
        <th>Наименование</th>
    </tr>
    <c:forEach var="operationTypeDtoRes" items="${operationTypeDtoResList}">
        <tr>
            <td>${operationTypeDtoRes.typeId}</td>
            <td>${operationTypeDtoRes.typeName}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
