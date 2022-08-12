<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 04.08.2022
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Отчетность</title>
    <style>
        <%@ include file="css/style.css"%>
    </style>
</head>
<body>

<header>
    <div id="header" style="background-color:#778899; height:8%;">
        <h1 style="margin-bottom:0;">Главный Заголовок Страницы</h1>
    </div>
</header>

<div id="container" style="width:100%">

    <div id="menu" style="background-color:#bebebe; height:100%; width:15%; float:left; ">
        <nav>
            <h2>Меню</h2>
            <p><a href="/warehouse/operations/order-form">Создать список товаров</a></p>
            <p><a href="/warehouse/operations/operation-form">Создать товарную операцию</a></p>
            <p><a href="/warehouse/products/add">Внести товар в БД</a></p>
            <p><a href="/warehouse/producers/add">Внести производителя в БД</a></p>
            <p><a href="/warehouse/countries/add">Внести страну в БД</a></p>
            <p><a href="/warehouse/products/get-by-name-form">Посмотреть товар</a></p>
            <p><a href="/warehouse/products/get-all">Посмотреть все товары</a></p>
            <p><a href="/warehouse/producers/get-by-name-form">Посмотреть производителя</a></p>
            <p><a href="/warehouse/producers/get-all">Посмотреть всех производителей</a></p>
            <p><a href="/warehouse/countries/get-all">Посмотреть все страны</a></p>
            <p><a href="/warehouse/operations/get-by-invoice-form">Посмотреть товарную операцию</a></p>
            <p><a href="/warehouse/operations/report-by-day">Отчет за день</a></p>
            <p><a href="/warehouse/operations/report-by-day-type-form">Отчет за день по операции</a></p>
            <p><a href="/warehouse/operations/report-by-period-form">Отчет за период</a></p>
            <p><a href="/warehouse/operations/report-by-period-type-form">Отчет за период по операции</a></p>
            <p><a href="/warehouse/user/add">Создать пользователя</a></p>
            <p><a href="/warehouse/user/get-by-login-form">Посмотреть пользователя</a></p>
            <p><a href="/warehouse/user/get-all">Посмотреть всех пользователей</a></p>
            <p><a href="/warehouse/user/delete-by-login-form">Удалить пользователя</a></p>
        </nav>
    </div>

    <div id="content" style="background-color:#d3d3d3; height:100%; width:85%; float:left;">

        <h3>Отчетность по выбранному типу товарной операции за текущий день</h3>
        <form:form modelAttribute="operationsDto" action="/warehouse/operations/report-by-day-type" method="post">
            <table>
                <tr>
                    <td>Выберите тип операции:</td>
                    <td><form:select path="typeName">
                        <c:forEach var="operationTypeDtoRes" items="${operationsDto.operationTypeList}">
                            <form:option value="${operationTypeDtoRes.typeName}"></form:option>
                        </c:forEach>
                    </form:select>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Ввод"></td>
                </tr>
            </table>
        </form:form>

    </div>
</div>

<footer>
    <div id="footer" style="background-color:#778899; clear:both; text-align:center;">
        © Uroki-HTML.ru
    </div>
</footer>

</body>
</html>
