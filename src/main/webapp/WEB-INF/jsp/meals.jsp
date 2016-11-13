<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>

<jsp:include page="fragments/headTag.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.12/css/jquery.dataTables.min.css">
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>

    <div class="jumbotron">
        <div class="container">
            <div class="shadow">
                <h3><fmt:message key="meals.title"/></h3>
                <div class="view-box">
                    <form id="filter" class="form-horizontal" method="post">
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="startDate">From Date:</label>
                            <div class="col-sm-2">
                                <input type="date" class="form-control" name="startDate" id="startDate">
                            </div>
                            <label class="control-label col-sm-2" for="endDate">To Date</label>
                            <div class="col-sm-2">
                                <input type="date" class="form-control" name="endDate" id="endDate">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="startTime">From Time</label>
                            <div class="col-sm-2">
                                <input type="time" class="form-control time-picker" name="startTime" id="startTime">
                            </div>
                            <label class="control-label col-sm-2" for="endTime">End Time</label>
                            <div class="col-sm-2">
                                <input type="time" class="form-control time-picker" name="endTime" id="endTime">
                            </div>
                        </div>
                        <button type="submit"><fmt:message key="app.filter"/></button>
                    </form>
                    <a class="btn btn-sm btn-info" onclick="add()"><fmt:message key="meals.add"/></a>
                    <br/>
                    <table class="table table-striped display" id="datatable">
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Description</th>
                            <th>Calories</th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <c:forEach items="${meals}" var="meal">
                            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                                <td>
                                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                        ${fn:formatDateTime(meal.dateTime)}
                                </td>
                                <td>${meal.description}</td>
                                <td>${meal.calories}</td>
                                <td><a class="btn btn-xs btn-primary edit" id="${user.id}"><fmt:message
                                        key="common.update"/></a></td>
                                <td><a class="btn btn-xs btn-danger delete" id="${user.id}"><fmt:message
                                        key="common.delete"/></a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>

            </div>
        </div>
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="meals.edit"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="datetime" class="control-label col-xs-3">Date</label>

                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" id="datetime" name="datetime"
                                   placeholder="Date and time">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3">Description</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description"
                                   placeholder="Description">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="Calories" class="control-label col-xs-3">Calories</label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="calories" name="calories"
                                   placeholder="Calories">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>


<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript">

    var ajaxUrl = 'ajax/meals/'
    var datatableApi;

    // $(document).ready(function () {
    $(function () {
        datatableApi = $('#datatable').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "aoColumns": [
                {
                    "mData": "dateTime"
                },
                {
                    "mData": "description"
                },
                {
                    "mData": "calories"
                },
                {
                    "sDefaultContent": "Edit",
                    "bSortable": false
                },
                {
                    "sDefaultContent": "Delete",
                    "bSortable": false
                }
            ],
            "aaSorting": [
                [
                    0,
                    "asc"
                ]
            ]
        });
        makeEditable();
    });

    $("#filter").submit(function (event) {
        var url = 'ajax/meals/filter/';

        // the script where you handle the form input.
        var response = $.ajax({
            type: "POST",
            url: url,
            data: $("#filter").serialize(), // serializes the form's elements.
        });

        event.preventDefault(); // avoid to execute the actual submit of the form.
        response.done(function (data) {
            datatableApi.fnClearTable();
            $.each(data, function (key, item) {
                datatableApi.fnAddData(item)
            });
            datatableApi.fnDraw();
        })
    });
</script>

</html>
