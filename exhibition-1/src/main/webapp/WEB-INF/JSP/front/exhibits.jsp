<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>展商管理系统</title>
    <link rel="stylesheet" href="layui/css/layui.css">
    <link rel="stylesheet" href="css/展商后台管理系统.css" />
    <script type="text/javascript" src="layui/layui.js"></script>
    <script type="text/javascript" src="js/holder.min.js" ></script>
    <script type="text/javascript" src="js/jquery.min.js" ></script>
    <script type="text/javascript" src="js/展商后台管理系统.js" ></script>
</head>
<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">展商管理系统</div>

        <ul class="layui-nav layui-layout-right">
            <a class="layui-nav-item" href="" style="color: forestgreen">退出</a>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="">产品管理</a>
                </li>
                <li class="layui-nav-item">
                    <a href="">上传展品</a>
                </li>
                <li class="layui-nav-item"><a href="">评论管理</a></li>
            </ul>
        </div>
    </div>

    <div class="layui-body ">
        <!-- 产品管理 -->
        <div class="text">
            <table class="layui-table">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>价格</th>
                    <th>销量</th>
                    <th>关键字</th>
                    <th>状态</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>商品名</td>
                    <td>300</td>
                    <td>34</td>
                    <td>电脑</td>
                    <td>已审核</td>
                </tr>
                <tr>
                    <td>..</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>..</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>..</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>..</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>..</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>..</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>..</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>..</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>..</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>..</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>..</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;"> </fieldset>
            <div id="demo0"></div>
        </div>
        <!--增加展品-->
        <div class="text" style="margin-top: 50px;">
            <form class="layui-form" action="">
                <div class="layui-form-item">
                    <label class="layui-form-label">展品名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="title" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">展品数量</label>
                    <div class="layui-input-block">
                        <input type="text" name="title" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">展品关键字</label>
                    <div class="layui-input-block">
                        <input type="text" name="title" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">展品价格</label>
                    <div class="layui-input-block">
                        <input type="text" name="title" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">展品简介</label>
                    <div class="layui-input-block">
                        <textarea name="desc" placeholder="请输入内容" class="layui-textarea"></textarea>
                    </div>
                </div>
                <button type="button" class="layui-btn" id="test1" style="margin: 20px 100px;">
                    <i class="layui-icon">&#xe67c;</i>商品图片
                </button>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
            </form>
        </div>
        <!--评论管理-->
        <div class="text">
            <!--用户头像-->
            <img src="holder.js/100x100"/>
            <span style="font-size: 10px;">商品简介：Holder 可直接在客户端渲染图片的占位。支持在线和离线，提供一个链式 API 对图像占位进行样式处理。</span>
            <p style="font-size: 10px; margin: 0 100px;">
                评论：
                <span>
			Holder 可直接在客户端渲染图片的占位。支持在线和离线，提供一个链式 API 对图像占位进行样式处理。
			<br/>
			<img src="holder.js/100x100">
			<img src="holder.js/100x100">
			<img src="holder.js/100x100">
		</span>
            </p>
            <img src="holder.js/100x100"/>
            <span style="font-size: 10px;">商品简介：Holder 可直接在客户端渲染图片的占位。支持在线和离线，提供一个链式 API 对图像占位进行样式处理。</span>
            <p style="font-size: 10px; margin: 0 100px;">
                评论：
                <span>
			Holder 可直接在客户端渲染图片的占位。支持在线和离线，提供一个链式 API 对图像占位进行样式处理。
			<br/>
			<img src="holder.js/100x100">
			<img src="holder.js/100x100">
			<img src="holder.js/100x100">
		</span>
            </p>
            <img src="holder.js/100x100"/>
            <span style="font-size: 10px;">商品简介：Holder 可直接在客户端渲染图片的占位。支持在线和离线，提供一个链式 API 对图像占位进行样式处理。</span>
            <p style="font-size: 10px; margin: 0 100px;">
                评论：
                <span>
			Holder 可直接在客户端渲染图片的占位。支持在线和离线，提供一个链式 API 对图像占位进行样式处理。
			<br/>
			<img src="holder.js/100x100">
			<img src="holder.js/100x100">
			<img src="holder.js/100x100">
		</span>
            </p>
            <img src="holder.js/100x100"/>
            <span style="font-size: 10px;">商品简介：Holder 可直接在客户端渲染图片的占位。支持在线和离线，提供一个链式 API 对图像占位进行样式处理。</span>
            <p style="font-size: 10px; margin: 0 100px;">
                评论：
                <span>
			Holder 可直接在客户端渲染图片的占位。支持在线和离线，提供一个链式 API 对图像占位进行样式处理。
			<br/>
			<img src="holder.js/100x100">
			<img src="holder.js/100x100">
			<img src="holder.js/100x100">
		</span>
            </p>
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">

            </fieldset>
            <div id="demo1"></div>
        </div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        展商管理系统
    </div>
</div>


</body>
</html>