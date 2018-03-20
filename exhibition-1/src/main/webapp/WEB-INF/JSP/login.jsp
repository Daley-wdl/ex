<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>展会登录(Exhibition)</title>
    <link rel="shortcut icon" href="${root}img/title.png" >
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${root}/static/bs/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${root}/static/css/login.css" />
    <link rel="stylesheet" href="${root}/static/css/main.css" />
    <link rel="stylesheet" href="${root}/static/css/jquery.idcode.css" />
    <link rel="stylesheet" href="${root}/static/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${root}/static/bs/js/jquery.min.js" ></script>
    <script type="text/javascript" src="${root}/static/bs/js/bootstrap.min.js" ></script>
    <script src="${root}/static/js/jquery.idcode.js"></script>
    <script src="https://cdn.bootcss.com/jquery.form/4.2.0/jquery.form.js"></script>
    <script type="text/javascript" src="${root}/static/js/login.js" ></script>
</head>
<body>
<div class="con">
    <div class="container z_mycontent">
        <div class="col-md-5 col-md-offset-1 picture">
            <img src="img/logo.png" />
        </div>
        <div class="col-md-6 all4">
            <div id="errorMsg">${sessionScope.message}</div>
            <form id="loginform" action="${root}/common/doLogin" method='post'  enctype="multipart/form-data">
                <p>
                <div class="z_name" >用户名:</div>
                <input type="text" name='username' id="username" class='auth' placeholder=" 用户名/账号">
                <span class='error' ></span>
                <span class='right'></span>
                </p>
                <p>
                <div class="z_name" >密码:</div>
                <input type="password" name="password" id="password" class='auth' placeholder=" 字母/数字/下划线">
                <span class='error'></span>
                <span class='right'></span>
                </p>
                <p style="height: 40px;line-height: 40px;padding-top: 5px;">
                    <label >选择身份:</label>
                    <label style="margin-left: 60px;">
                        <input type="radio" name="yzm" >展商
                    </label>
                    <label style="margin-left: 20px;">
                        <input type="radio" name="yzm" >管理员
                    </label>
                </p>
                <p>
                <div class="lblVerification z_name">验证码:</div>
                <input type="text" id="Txtidcode" class="txtVerification auth" name='yzm'/>
                <span id="idcode"></span>
                <span class='error'></span>
                <span class='right'></span>
                </p>
                <p>
                    <input type="button" value="提交" id="btns" class="z_mysubmit">
                </p>
            </form>
        </div>
    </div>
    <footer class="footer mt-20 yejiao">
        <div class="container-fluid">
            <nav> <a href="#" target="_blank">关于我们</a> <span class="pipe">|</span> <a href="#" target="_blank">联系我们</a> <span class="pipe">|</span> <a href="#" target="_blank">法律声明</a> </nav>
            <p>Copyright &copy;2017 展会  All Rights Reserved. <br>
                <a href="http://www.miitbeian.gov.cn/" target="_blank" rel="nofollow">鄂ICP备0000000号</a><br>
            </p>
        </div>
    </footer>
</div>
</body>

<script type="text/javascript">

</script>
</html>