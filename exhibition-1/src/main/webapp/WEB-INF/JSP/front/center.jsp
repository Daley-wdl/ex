<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="layui/css/layui.css" />
    <link rel="stylesheet" href="css/comment.css" />
    <link rel="stylesheet" href="css/center.css" />
    <title>游客个人中心</title>
</head>
<body>
<div class="jd_laylout">
    <header class="jd_header">
        <div class="jd_header_box" style="opacity: 0.5;"><!--把所有的内容放在一个父容器里面-->
            <div style="height: 8px;width: 100%;"></div>
            <!--<input type="text"placeholder="搜索" autocomplete="off" class="layui-input z_input">-->
            <i class="layui-icon" style="font-size: 25px; color: #F5F5F;margin-left: 2%;">&#xe603;</i>
            <div style="height: 2px;width: 100%;"></div>
        </div>
    </header>
</div>
<div class="z_blank" style="height: 70px;background:oldlace"></div>
<!--主要内容-->
<!--个人信息部分-->
<div class="layui-container" style="background: oldlace">
    <div class="layui-col-md1">
        <img src="img/tx.jpg" class="z_myphoto">
    </div>
    <div class="layui-col-md1" style="height: 60px;">
        <h3 style="padding-top: 10px;">用户名</h3>
    </div>
</div>
<!--个人信息部分-->
<hr style="visibility: hidden;"/>
<!--我的订单部分-->
<nav class="jd_nav">
    <ul class="clearfix">
        <li>
            <a href="#"><img src="img/pay.PNG"/><p>待付款</p></a>
        </li>
        <li>
            <a href="#"><img src="img/pay1.PNG"/><p>待发货</p></a>
        </li>
        <li>
            <a href="#"><img src="img/pay2.PNG"/><p>待收货</p></a>
        </li>
        <li>
            <a href="#"><img src="img/pay2.PNG"/><p>升级订单</p></a>
        </li>
    </ul>
</nav>
<!--我的订单部分-->
<hr style="visibility: hidden;"/>
<div class="z_myblank"></div>
<!--详情分类-->
<div class="layui-container">
    <div class="z_meterial">
        <div class="z_left"></div>
        <span class="z_time">我的资产</span>
        <div class="z_time">
            <a>更多>></a>
        </div>
    </div>
</div>
<hr/>
<div class="layui-container">
    <div class="z_order">
        <div class="z_order_content">
            <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe60c;</i>
            <p>零钱</p>
        </div>
    </div>
    <div class="z_order">
        <div class="z_order_content">
            <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe60c;</i>
            <p>零钱</p>
        </div>
    </div>
    <div class="z_order">
        <div class="z_order_content">
            <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe60c;</i>
            <p>零钱</p>
        </div>
    </div>
    <div class="z_order">
        <div class="z_order_content">
            <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe60c;</i>
            <p>零钱</p>
        </div>
    </div>
</div>
<!--详情分类-->
<hr style="visibility: hidden;"/>
<div class="z_myblank"></div>
<!--详情分类-->
<div class="layui-container">
    <div class="z_meterial">
        <div class="z_left"></div>
        <span class="z_time">展会服务</span>
        <div class="z_time">
            <a>更多>></a>
        </div>
    </div>
</div>
<hr/>
<div class="layui-container">
    <div class="z_order">
        <div class="z_order_content">
            <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe60c;</i>
            <p>服务</p>
        </div>
    </div>
    <div class="z_order">
        <div class="z_order_content">
            <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe60c;</i>
            <p>服务</p>
        </div>
    </div>
    <div class="z_order">
        <div class="z_order_content">
            <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe60c;</i>
            <p>服务</p>
        </div>
    </div>
    <div class="z_order">
        <div class="z_order_content">
            <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe60c;</i>
            <p>服务</p>
        </div>
    </div>
    <div class="z_order">
        <div class="z_order_content">
            <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe60c;</i>
            <p>服务</p>
        </div>
    </div>
    <div class="z_order">
        <div class="z_order_content">
            <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe60c;</i>
            <p>服务</p>
        </div>
    </div>
    <div class="z_order">
        <div class="z_order_content">
            <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe60c;</i>
            <p>服务</p>
        </div>
    </div>
    <div class="z_order">
        <div class="z_order_content">
            <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe60c;</i>
            <p>服务</p>
        </div>
    </div>
</div>
<!--详情分类-->
<hr style="visibility: hidden;"/>
<div class="z_myblank"></div>
<!--账户与安全-->
<div class="layui-container">
    <div class="z_meterial">
        <span class="z_time" style="padding-bottom: 10px;">账户与安全</span>
        <div class="z_time">
            <a style="padding-bottom: 10px;">></a>
        </div>
    </div>
</div>
<!--账户与安全-->
<hr style="margin: 0;"/>
<!--账户与安全-->
<div class="layui-container">
    <div class="z_meterial">
        <span class="z_time" style="padding-bottom: 10px;">展会客服</span>
        <div class="z_time">
            <a style="padding-bottom: 10px;">></a>
        </div>
    </div>
</div>
<!--账户与安全-->
<hr style="margin: 0;"/>
<!--账户与安全-->
<div class="layui-container">
    <div class="z_meterial">
        <span class="z_time" style="padding-bottom: 10px;">关于展会</span>
        <div class="z_time">
            <a style="padding-bottom: 10px;">></a>
        </div>
    </div>
</div>
<!--账户与安全-->
<!--主要内容-->
<script src="layui/layui.js"></script>

</body>
</html>
