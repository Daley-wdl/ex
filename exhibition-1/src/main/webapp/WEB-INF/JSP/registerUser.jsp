<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户注册</title>

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet"
          href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous"/>
    <style type="text/css">
    </style>
</head>

<body>
<div class="container">
    <div class="page-header">
        <h1>请先注册</h1>
    </div>

    <form class="form-horizontal" role="form" id="sendForm"
          action="${root}/common/register/user" enctype="multipart/form-data" method="post" >
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="username" name="username" placeholder="请输入名字">
                <label class="label label-danger" id="checkUsername">长度为0-30</label>
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">密码</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
                <label class="label label-danger" id="checkPassword">长度为1-30</label>
            </div>
        </div>

        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">密码确认</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="password2" placeholder="请确认密码">
                <label class="label label-danger" id="checkPassword2">两次密码输入不一致</label>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="button" class="btn btn-primary btn-lg " id="sendBtn">注册</button>
                <button type="button" class="btn btn-primary btn-lg " id="toLogin">返回登录</button>
            </div>

        </div>

    </form>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery.form/4.2.0/jquery.form.js"></script>
<script type="text/javascript">
    $(function () {
        setLabel(false);//隐藏所有错误标签
        $("#sendBtn").click(function () {
            setLabel(false);
            var flag = checkInput();
            if (flag) {
                $("#sendForm").ajaxSubmit({
                    type : "post",
                    url:"${root}/common/register/user",
                    success : function (data) {
                        console.log(data.message);
                        if (data.status=="0" || data.status==0) {
                            alert("注册成功！");
                        } else {
                            alert(data.message);
                        }
                    }
                });
//                return false;
            }
        })
        
        $("#toLogin").click(function () {
            window.location.href='${root}/common/login';
        })
    })

    function setLabel(flag) {
        if(false){
            $(".label").show(300);
        } else {
            $(".label").hide();
        }
    }

    function checkInput() {
        var flag = true;
        var username=$("#username").val();
        var password=$("#password").val();
        var password2=$("#password2").val();

        if (username.length<1 || username.length>30){
            flag = false;
            $("#checkUsername").show(300);
        }
        if(password.length<1 || password.length>30) {
            flag = false;
            $("#checkPassword").show(300);
        }
        if (password!=password2) {
            flag = false;
            $("#checkPassword2").show(300);
        }
        return flag;
    }

</script>

</body>

</html>