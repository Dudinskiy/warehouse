<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 31.07.2022
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Страна</title>
    <style>
        <%@ include file="css/style.css"%>
    </style>
</head>
<body>
<h2>Посмотреть страну:</h2>
<form:form modelAttribute="countriesDto" action="/warehouse/countries/get-by-name" method="post">
    <table>
        <tr>
            <td>Введите наименование:</td>
            <td><form:input path="countryName"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Ввод"></td>
        </tr>
    </table>
</form:form>
</body>
</html>
