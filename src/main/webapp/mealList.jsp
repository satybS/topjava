<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://javawebinar.ru/topjava/util/DateUtil" prefix="f" %>

<html>
<head>
    <title>Meal List</title>
    <style type="text/css">
        body {
            font-family: arial, helvetica, sans-serif;
            font-size: 80%;
            color: black;
            background-color: #f5f5f0;
            margin: 1em;
            padding: 0;
        }

        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
            background-color: #f9f9f9
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg tr {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }

    </style>
</head>
<body>
<table class="tg">
    <tr>
        <td><h2>Date</h2></td>
        <td><h2>Description</h2></td>
        <td><h2>Calories</h2></td>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <c:if test="${meal.exceed}">
                <tr>
                    <td><span style="color:red"><input type="datetime-local" value=${meal.dateTime}></span></td>
                    <td><span style="color:red">${meal.description}</span></td>
                    <td><span style="color:red">${meal.calories}</span></td>
                </tr>
        </c:if>
        <c:if test="${not meal.exceed}">
            <tr>
                <td><span style="color:green"><input type="datetime-local" value=${meal.dateTime}></span></td>
                <td><span style="color:green">${meal.description}</span></td>
                <td><span style="color:green">${meal.calories}</span></td>
            </tr>
        </c:if>
    </c:forEach>
</table>
</body>
</html>
