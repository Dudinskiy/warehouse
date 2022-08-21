<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 02.08.2022
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Список товаров</title>
    <style>
        <%@ include file="css/style.css"%>
    </style>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
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

<div id="container" style="width:100%;">
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

        <h3>Добавить товар:</h3>

        <form:form modelAttribute="operationsDto" action="/warehouse/operations/add-order" method="post">
            <table>
                <tr>
                    <td>Производитель:</td>
                    <td><form:select path="producerName" id="producers" onchange="getProductList()">
                        <form:option value="">Производитель не выбран</form:option>
                        <c:forEach var="producersFullDtoRes" items="${operationsDto.allProducers}">
                            <form:option value="${producersFullDtoRes.producerName}"></form:option>
                        </c:forEach>
                    </form:select></td>
                </tr>
                <tr>
                    <td>Товар:</td>
                    <td><select id="products" name="productName">
                        <option value="">Товар не выбран</option>
                    </select></td>
                    <td>Количество:</td>
                    <td><form:input path="operationProdAmount"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Добавить"></td>
                </tr>
            </table>
        </form:form>

        <h3>Добавленные товары:</h3>
        <table border="1" cellspacing="2" , cellpadding="10">
            <tr>
                <th>№</th>
                <th>Инд. номер</th>
                <th>Наименование</th>
                <th>Производитель</th>
                <th>Количество</th>
                <th>Количество на складе</th>
                <th>Удалить</th>
            </tr>
            <c:forEach var="product" items="${operationsDto.productList}">
                <tr>
                    <td>${operationsDto.productList.indexOf(product)+1}</td>
                    <td>${product.productId}</td>
                    <td>${product.productName}</td>
                    <td>${product.producerName}</td>
                    <td>${product.operationProdAmount}</td>
                    <td>${product.currentProdAmount}</td>
                    <td><a href="/warehouse/operations/delete-order/${product.productId}">Удалить</a></td>
                </tr>
            </c:forEach>
        </table>
        <h4><a href="/warehouse/operations/clean-order">Очистить список товаров</a></h4>
        <h4><a href="/warehouse/operations/operation-form">Создать товарную операцию</a></h4>
    </div>
</div>

<script>
    function getProductList() {
        let selectBox = document.getElementById("producers");
        let producerName = selectBox.options[selectBox.selectedIndex].value;
        let token = document.querySelector('meta[name="_csrf"]').content;
        let csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
        let port = ${pageContext.request.serverPort};
        let url = 'http://localhost:' + port + '/warehouse/rest/product/get-by-producer';

        let xhttp = new XMLHttpRequest();

        xhttp.onload = function () {
            if (xhttp.status == 200) {
                let producersFullDtoResList = JSON.parse(xhttp.response);
                let html = '<option value="">Товар не выбран</option>';

                for (let i = 0; i < producersFullDtoResList.length; i++) {
                    html = html + '<option value="' + producersFullDtoResList[i].productName + '">'
                        + producersFullDtoResList[i].productName + '</option>'
                }
                document.getElementById("products").innerHTML = html;
            }
            return false;
        }
        xhttp.open("POST", url, true);
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.setRequestHeader(csrfHeader, token);
        xhttp.send(JSON.stringify({
            productId: 0, productName: '', producerName: producerName
            , countryName: '', price: 0, productAmount: 0
        }));
    }

    let elem1 = document.getElementById("producers");
    elem1.addEventListener('change', getProductList);
</script>

<footer>
    <div id="footer" style="background-color:#778899; clear:both; text-align:center;">
        © Александр Дудинский
    </div>
</footer>

</body>
</html>
