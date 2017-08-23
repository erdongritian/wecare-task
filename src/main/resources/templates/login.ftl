<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>任务管理</title>
    <#--<script src="/js/jquery-1.8.1.min.js"></script>-->
    <#--<script src="/js/jquery.form.js"></script>-->

    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/jquery.form.js"></script>
</head>
<style>
    html, body {
        height: 100%;
        width: 100%;
        padding: 0;
        margin: 0;
    }
    body, div, span, header, footer, nav, section, aside, article,
    ul, dl, dt, dd, li, a, p, h1, h2, h3, h4, h5, h6, i, b,
    textarea, button, input, select, figure, figcaption {
        padding: 0;
        margin: 0;
        list-style: none;
        font-style: normal;
        text-decoration: none;
        border: none;
        font-family: "Microsoft Yahei", sans-serif;
        -webkit-tap-highlight-color: transparent;
        -webkit-font-smoothing: antialiased;
    }
    .login_page{
        background-color: #324057;
    }
    .fillcontain {
        height: 100%;
        width: 100%;
    }
    .form_contianer{
        width: 320px;
        height: 180px;
        position: absolute;
        top: 50%;
        left: 50%;
        margin-top: -105px;
        margin-left: -160px;
        padding: 25px;
        border-radius: 5px;
        text-align: center;
        background-color: #fff;
        box-sizing: initial;
        -webkit-box-sizing: initial;
    }
    .manage_tip{
        position: absolute;
        width: 100%;
        top: -100px;
        left: 0;
    }
    .manage_tip p {
        font-size: 34px;
        color: #fff;
    }
    .el-input__inner {
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
        background-color: #fff;
        background-image: none;
        border-radius: 4px;
        border: 1px solid #bfcbd9;
        box-sizing: border-box;
        color: #1f2d3d;
        font-size: inherit;
        height: 36px;
        line-height: 1;
        outline: 0;
        padding: 3px 10px;
        transition: border-color .2s cubic-bezier(.645,.045,.355,1);
    }
    .el-input, .el-input__inner {
        width: 100%;
        display: inline-block;
    }

    .el-button {
        display: inline-block;
        line-height: 1;
        white-space: nowrap;
        cursor: pointer;
        background: #fff;
        border: 1px solid #c4c4c4;
        color: #1f2d3d;
        margin: 0;
        padding: 10px 15px;
        padding-top: 10px;
        padding-right: 15px;
        padding-bottom: 10px;
        padding-left: 15px;
        border-radius: 4px;
    }
    .el-button--primary {
        color: #fff;
        background-color: #20a0ff;
        border-color: #20a0ff;
    }
    .form_contianer .submit_btn {
        width: 100%;
        font-size: 16px;
    }
    .el-form-item{
        margin-bottom: 25px;
    }
</style>
<body>
<div class="login_page fillcontain">
        <section class="form_contianer">
            <div class="manage_tip">
                <p>任务管理</p>
            </div>

            <form class="el-form" action="/login" id="login_form" method="POST">
                <div  class="el-form-item is-required">
                    <div class="el-form-item__content">
                        <div class="el-input">
                            <input autocomplete="off" id="username" name="username" placeholder="用户名" type="text" rows="2" validateevent="true" class="el-input__inner" required>
                            </div>
                    </div>
                </div>
                <div  class="el-form-item is-required">
                    <div class="el-form-item__content">
                        <div class="el-input">
                        <input autocomplete="off" id="password" name="password" placeholder="密码" type="password" rows="2" validateevent="true" class="el-input__inner" required>
                     </div>
                    </div>
                </div>
                <div class="el-form-item">
                    <div class="el-form-item__content">
                        <button type="button" class="el-button submit_btn el-button--primary" id="submit_btn">
                           <span>登陆</span>
                        </button>
                    </div>
                </div>
            </form>
        </section>
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

<script type="text/javascript">
    $(function($) {
        $('#username').focus();
        // 提交表单
        $('#submit_btn').click(function() {
            if ($('#username').val() == '') {
                showMes('username is invaild');
                $('#username').focus();
            } else if ($('#password').val() == '') {
                showMes('password is invaild');
                $('#password').focus();
            } else {
//                $('#login_form').submit();
                $('#login_form').ajaxSubmit(function(data) {
                    if(data.status){
                        window.location.href = '/task/list';
                    } else {
                        showMes(data.message);
                    }
                });

            }
        });

        //支持Enter键登录
        document.onkeydown = function(e) {
            if ($(".bac").length == 0) {
                if (!e)
                    e = window.event;
                if ((e.keyCode || e.which) == 13) {
                    $("#submit_btn").focus();
                }
            }
        };

    });




    function showMes(mes) {
        $("#mes").text(mes);
        $("#mesModal").modal('show');
    }
</script>

</body>
</html>
