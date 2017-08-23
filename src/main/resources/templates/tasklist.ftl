<!DOCTYPE html>

<html lang="en">

<!-- Bootstrap core CSS -->
<link href="/css/bootstrap.min.css" rel="stylesheet">
<script src="/js/jquery-3.2.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery.form.js"></script>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand">WeCare Task</a>
        </div>
        <!-- Note that the .navbar-collapse and .collapse classes have been removed from the #navbar -->
        <div id="navbar">
        <#--<form class="navbar-form navbar-right">-->
                <#--<div class="form-group">-->
                    <#--<input type="text" class="form-control" placeholder="UserName" id="username">-->
                <#--</div>-->
                <#--<div class="form-group">-->
                    <#--<input type="text" class="form-control" placeholder="SensorCode" id="sensorcode">-->
                <#--</div>-->
                <#--<button type="button" class="btn btn-default" id="submitBut">Search</button>-->
            <#--</form>-->

            <a href="/logout" title="Logout" class="navbar-text navbar-right">Logout</a>
        </div>
    </div>
</nav>

<div class="main" style="padding: 40px;">
    <h1 class="page-header">Task List</h1>
    <button type="button" class="btn btn-primary" id="add">Add Task</button>
    <div class="table-responsive">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ServiceName</th>
                <th>Description</th>
                <th>CronExpression</th>
                <th>Status</th>
                <th>PreviousFireTime</th>
                <th>NextFireTime</th>
                <th>&nbsp;</th>
            </tr>
            </thead>
            <tbody id="collect-body">
            <#if list??>
                <#list list as collect>
                <tr>
                    <td>${collect.cronTrigger.key.name!''}</td>
                    <td>${collect.cronTrigger.description!''}</td>
                    <td>${collect.cronTrigger.cronExpression!''}</td>
                    <td>${collect.stateName!''}</td>
                    <td>
                        <#if collect.cronTrigger.previousFireTime??>
                    ${collect.cronTrigger.previousFireTime?string("yyyy-MM-dd HH:mm:ss")}
                    </#if>
                    </td>
                    <td>
                        <#if collect.cronTrigger.nextFireTime??>
                        ${collect.cronTrigger.nextFireTime?string("yyyy-MM-dd HH:mm:ss")}
                        </#if>
                    </td>
                    <td>
                        <div>
                            <#if collect.stateName == "NORMAL">
                                <button type="button" class="btn btn-default" aria-label="Left Align"
                                        title="Pause" onclick="pauseTask('${collect.cronTrigger.key.name!''}');">
                                    <span class="glyphicon glyphicon-pause" aria-hidden="true"></span>
                                </button>
                            </#if>
                            <#if collect.stateName == "PAUSED">
                                <button type="button" class="btn btn-default" aria-label="Left Align"
                                        title="Play" onclick="playTask('${collect.cronTrigger.key.name!''}');">
                                    <span class="glyphicon glyphicon-play" aria-hidden="true"></span>
                                </button>
                            </#if>
                            <button type="button" class="btn btn-default" aria-label="Right Align"
                                    title="Test" onclick="testTask('${collect.cronTrigger.key.name!''}');">
                                <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                            </button>
                            <button type="button" class="btn btn-default" aria-label="Right Align"
                                    title="Modify" onclick="modifyTask('${collect.cronTrigger.key.name!''}',
                                    '${collect.cronTrigger.description!''}','${collect.cronTrigger.cronExpression!''}');">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                            </button>
                            <button type="button" class="btn btn-default" aria-label="Justify"
                                    title="Remove" onclick="removeTask('${collect.cronTrigger.key.name!''}');">
                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                            </button>
                        </div>
                    </td>

                </tr>
                </#list>
            </#if>

            </tbody>
        </table>
        <div id="waiting" style="text-align: center;display: none;padding-top:12%;">
            <img src="/img/waiting.gif"
        </div>
    </div>
</div>

<div class="modal fade" tabindex="-1" role="dialog" id="myModal" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add Or Modify Task</h4>
            </div>
            <div class="modal-body">
                <form id="addTaskForm" action="/task/add" method="post">
                    <div class="form-group">
                        <label for="exampleInputEmail1">ServiceName</label>
                        <input type="text" class="form-control" id="sName" name="taskService" placeholder="ServiceName"
                               required>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Description</label>
                        <input type="text" class="form-control" id="descri" name="description"
                               placeholder="Description">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">CronExpression(eg:0/30 * * * * ?)</label>
                        <input type="text" class="form-control" id="cronExpr" name="cronExpression"
                               placeholder="CronExpression" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="savaTask">Save</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" tabindex="-1" role="dialog" id="mesModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Message</h4>
            </div>
            <div class="modal-body">
                <p id="mes"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

</body>

<script type="text/javascript">
    var mesFlag=0;
    $(function () {
        $('#savaTask').click(function () {
            $('#addTaskForm').ajaxSubmit(function (data) {
                $('#myModal').modal('hide');
                if(!data.status){
                    mesFlag=1;
                }
                showMes(data.message);
            });
        });

        $('#add').click(function () {
            $("#sName").val("");
            $("#descri").val("");
            $("#cronExpr").val("");
            $("#sName").removeAttr("readonly");
            $("#addTaskForm").attr("action", "/task/add");
            $('#myModal').modal('show');
        });

    });

    function pauseTask(serName) {
        $.get('/task/pause/' + serName, function (data) {
            showMes(data.message);
        });
    }

    function playTask(serName) {
        $.get('/task/play/' + serName, function (data) {
            showMes(data.message);
        });
    }

    function testTask(serName) {
        $.get('/task/test/' + serName, function (data) {
            showMes(data.message);
        });
    }

    function modifyTask(serName, desc, cronExpr) {
        $("#sName").attr("readonly","readonly");
        $("#sName").val(serName);
        $("#descri").val(desc);
        $("#cronExpr").val(cronExpr);
        $("#addTaskForm").attr("action", "/task/modify");
        $('#myModal').modal('show');
    }

    function removeTask(serName) {
        $.get('/task/delete/' + serName, function (data) {
            showMes(data.message);
        });
    }

    function showMes(mes) {
        $("#mes").text(mes);
        $("#mesModal").modal('show');
    }

    $('#mesModal').on('hidden.bs.modal', function (e) {
        if(mesFlag==0){
            window.location.href = '/task/list';
        }
        if(mesFlag==1){
            $('#myModal').modal('show');
            mesFlag=0;
        }
    });


</script>

</html>
