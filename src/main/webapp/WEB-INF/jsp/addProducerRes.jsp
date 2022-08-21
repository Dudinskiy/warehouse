<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 31.07.2022
  Time: 22:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        <%@ include file="css/style.css"%>
    </style>
</head>
<body>

<header>
    <div class="headerContainer">
        <div class="headerBox">
            <div class="mainTitle" id="header">
                <h1 style="margin-bottom:0;">Система управления складом</h1>
            </div>
            <div class="login">
                <p>
                    <c:if test="${not empty pageContext.request.userPrincipal}">
                        Текущий пользователь: <c:out value="${pageContext.request.userPrincipal.name}"></c:out>
                    </c:if>
                </p>
                <p><a href="/warehouse/login">Войти</a> &nbsp; &nbsp;
                    <a href="/warehouse/logout">Выйти</a></p>
            </div>
        </div>
    </div>
</header>

<div id="container" style="width:100%">

    <div id="menu" style="background-color:#bebebe; height:100%; width:15%; float:left; ">
        <nav>
            <h2>Меню</h2>
            <p><a href="/warehouse/operations/order-form">Создать список товаров</a></p>
            <p><a href="/warehouse/operations/clean-order">Очистить список товаров</a></p>
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
            <p><a href="/warehouse/reference">Справка</a></p>
        </nav>
    </div>

    <div id="content" style="background-color:#d3d3d3; height:100%; width:85%; float:left;">
        <p>${response}</p>
    </div>
</div>

<footer>
    <div id="footer" style="background-color:#778899; clear:both; text-align:center;">
        © Александр Дудинский
    </div>
</footer>

</body>
</html>
