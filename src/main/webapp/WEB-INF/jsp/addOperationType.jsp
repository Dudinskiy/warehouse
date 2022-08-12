<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 30.07.2022
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Товарные операции</title>
    <style>
        <%@ include file="css/style.css"%>
    </style>
</head>
<body>
<h2>Добавить вид операции:</h2>
<form:form modelAttribute="operationTypeDto" action="/warehouse/operation-type/create" method="post">
    <table>
        <tr>
            <td>Введите наименование:</td>
            <td><form:input path="typeName"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Сохранить"></td>
        </tr>
    </table>
</form:form>
</body>
</html>
