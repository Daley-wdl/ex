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
    <title>展会智能管理系统</title>
    <link rel="stylesheet" href="../tools/layui/css/layui.css">
    <link rel="stylesheet" href="../css/index.css" />
    <script src="../js/jquery.js"></script>
    <script src="../tools/kd/kindeditor-min.js"></script>
</head>
<body style="overflow: hidden;">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">展会智能管理系统</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="">控制台</a></li>
            <li class="layui-nav-item"><a href="">商品管理</a></li>
            <li class="layui-nav-item"><a href="">用户</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="">邮件管理</a></dd>
                    <dd><a href="">消息管理</a></dd>
                    <dd><a href="">授权管理</a></dd>
                </dl>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    管理员名称
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="">退出</a></li>
        </ul>
    </div>

    <div class="layui-bg-black layui-col-md1" id="z_nav" style="width: 200px;height: 1080px;">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree menu"  lay-filter="test">
                <li class="layui-nav-item active"><a href="">资讯管理</a></li>
                <li class="layui-nav-item"><a href="">产品管理</a></li>
                <li class="layui-nav-item"><a href="">展商管理</a></li>
                <li class="layui-nav-item"><a href="">评论管理</a></li>
                <li class="layui-nav-item"><a href="">会员管理</a></li>
                <li class="layui-nav-item"><a href="">系统统计</a></li>
                <li class="layui-nav-item"><a href="">系统管理</a></li>
            </ul>
        </div>
    </div>

    <div class="layui-body layui-col-md-11" >
        <!-- 内容主体区域 -->
        <div  class="info">
            <div class='tab-pane active fade in z_content' >

                <form action="" style="margin: 10px 0 0 10%;width: 90%;">
                    <p>
                        <textarea id="tid"></textarea>
                    </p>
                    <p style="padding-top: 10px;margin-left: 35%;">
                        <button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="demo1" style="width: 120px;">发布     </button>
                        <!--<button class="layui-btn layui-btn-normal"  style="width: 150px;margin:10px 0 20px 44%">提交     </button>-->
                    </p>
                </form>
                <hr />
                <!--已发布新闻动态-->
                <div class="layui-row" style="margin: 0 0 20px 5%;">
                    <div class="layui-col-space1" ></div>
                    <div class="layui-col-md2" style="height: 100px;">
                        <img src="img/news.png" class="z_photo" >
                    </div>
                    <div class="layui-col-md9"style="height: 100px;margin-left: 10px;">
                        <p><strong>展品关键字：***</strong><small>  <i class="layui-icon" style="font-size: 15px; color:#ADADAD;">&#xe60e;</i>    时间：2017-09-14</small></p>
                        <p class="z_intro">简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                        <p class="text-muted">
                            <small>
                                <i class="layui-icon" style="font-size: 15px; ">&#xe650;</i>关注:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c6;</i>点赞：21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c5;</i>踩：21
                                <i class="layui-icon" style="font-size: 15px; ">&#xe600;</i>收藏:20
                                <i class="layui-icon" style="font-size: 15px;">&#xe63a;</i>评论:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe628;</i>浏览:68
                            </small>
                        </p>
                    </div>
                </div>
                <!--已发布新闻动态-->
                <!--已发布新闻动态-->
                <div class="layui-row" style="margin: 0 0 20px 5%;">
                    <div class="layui-col-space1" ></div>
                    <div class="layui-col-md2" style="height: 100px;">
                        <img src="img/news.png" class="z_photo" >
                    </div>
                    <div class="layui-col-md9"style="height: 100px;margin-left: 10px;">
                        <p><strong>展品关键字：***</strong><small>  <i class="layui-icon" style="font-size: 15px; color:#ADADAD;">&#xe60e;</i>    时间：2017-09-14</small></p>
                        <p class="z_intro">简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                        <p class="text-muted">
                            <small>
                                <i class="layui-icon" style="font-size: 15px; ">&#xe650;</i>关注:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c6;</i>点赞：21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c5;</i>踩：21
                                <i class="layui-icon" style="font-size: 15px; ">&#xe600;</i>收藏:20
                                <i class="layui-icon" style="font-size: 15px;">&#xe63a;</i>评论:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe628;</i>浏览:68
                            </small>
                        </p>
                    </div>
                </div>
                <!--已发布新闻动态-->
                <!--已发布新闻动态-->
                <div class="layui-row" style="margin: 0 0 20px 5%;">
                    <div class="layui-col-space1" ></div>
                    <div class="layui-col-md2" style="height: 100px;">
                        <img src="img/news.png" class="z_photo">
                    </div>
                    <div class="layui-col-md9"style="height: 100px;margin-left: 10px;">
                        <p><strong>展品关键字：***</strong><small>  <i class="layui-icon" style="font-size: 15px; color:#ADADAD;">&#xe60e;</i>    时间：2017-09-14</small></p>
                        <p class="z_intro">简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                        <p class="text-muted">
                            <small>
                                <i class="layui-icon" style="font-size: 15px; ">&#xe650;</i>关注:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c6;</i>点赞：21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c5;</i>踩：21
                                <i class="layui-icon" style="font-size: 15px; ">&#xe600;</i>收藏:20
                                <i class="layui-icon" style="font-size: 15px;">&#xe63a;</i>评论:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe628;</i>浏览:68
                            </small>
                        </p>
                    </div>
                </div>
                <!--已发布新闻动态-->
                <!--已发布新闻动态-->
                <div class="layui-row" style="margin: 0 0 20px 5%;">
                    <div class="layui-col-space1" ></div>
                    <div class="layui-col-md2" style="height: 100px;">
                        <img src="img/news.png" class="z_photo" >
                    </div>
                    <div class="layui-col-md9"style="height: 100px;margin-left: 10px;">
                        <p><strong>展品关键字：***</strong><small>  <i class="layui-icon" style="font-size: 15px; color:#ADADAD;">&#xe60e;</i>    时间：2017-09-14</small></p>
                        <p class="z_intro">简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                        <p class="text-muted">
                            <small>
                                <i class="layui-icon" style="font-size: 15px; ">&#xe650;</i>关注:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c6;</i>点赞：21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c5;</i>踩：21
                                <i class="layui-icon" style="font-size: 15px; ">&#xe600;</i>收藏:20
                                <i class="layui-icon" style="font-size: 15px;">&#xe63a;</i>评论:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe628;</i>浏览:68
                            </small>
                        </p>
                    </div>
                </div>
                <!--已发布新闻动态-->
                <!--已发布新闻动态-->
                <div class="layui-row" style="margin: 0 0 20px 5%;">
                    <div class="layui-col-space1" ></div>
                    <div class="layui-col-md2" style="height: 100px;">
                        <img src="img/news.png" class="z_photo" >
                    </div>
                    <div class="layui-col-md9"style="height: 100px;margin-left: 10px;">
                        <p><strong>展品关键字：***</strong><small>  <i class="layui-icon" style="font-size: 15px; color:#ADADAD;">&#xe60e;</i>    时间：2017-09-14</small></p>
                        <p class="z_intro">简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                        <p class="text-muted">
                            <small>
                                <i class="layui-icon" style="font-size: 15px; ">&#xe650;</i>关注:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c6;</i>点赞：21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c5;</i>踩：21
                                <i class="layui-icon" style="font-size: 15px; ">&#xe600;</i>收藏:20
                                <i class="layui-icon" style="font-size: 15px;">&#xe63a;</i>评论:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe628;</i>浏览:68
                            </small>
                        </p>
                    </div>
                </div>
                <!--已发布新闻动态-->
                <!--已发布新闻动态-->
                <div class="layui-row" style="margin: 0 0 20px 5%;">
                    <div class="layui-col-space1" ></div>
                    <div class="layui-col-md2" style="height: 100px;">
                        <img src="img/news.png" class="z_photo" >
                    </div>
                    <div class="layui-col-md9"style="height: 100px;margin-left: 10px;">
                        <p><strong>展品关键字：***</strong><small>  <i class="layui-icon" style="font-size: 15px; color:#ADADAD;">&#xe60e;</i>    时间：2017-09-14</small></p>
                        <p class="z_intro">简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                        <p class="text-muted">
                            <small>
                                <i class="layui-icon" style="font-size: 15px; ">&#xe650;</i>关注:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c6;</i>点赞：21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c5;</i>踩：21
                                <i class="layui-icon" style="font-size: 15px; ">&#xe600;</i>收藏:20
                                <i class="layui-icon" style="font-size: 15px;">&#xe63a;</i>评论:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe628;</i>浏览:68
                            </small>
                        </p>
                    </div>
                </div>
                <!--已发布新闻动态-->
                <!--已发布新闻动态-->
                <div class="layui-row" style="margin: 0 0 20px 5%;">
                    <div class="layui-col-space1" ></div>
                    <div class="layui-col-md2" style="height: 100px;">
                        <img src="img/news.png" class="z_photo" >
                    </div>
                    <div class="layui-col-md9"style="height: 100px;margin-left: 10px;">
                        <p><strong>展品关键字：***</strong><small>  <i class="layui-icon" style="font-size: 15px; color:#ADADAD;">&#xe60e;</i>    时间：2017-09-14</small></p>
                        <p class="z_intro">简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                        <p class="text-muted">
                            <small>
                                <i class="layui-icon" style="font-size: 15px; ">&#xe650;</i>关注:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c6;</i>点赞：21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c5;</i>踩：21
                                <i class="layui-icon" style="font-size: 15px; ">&#xe600;</i>收藏:20
                                <i class="layui-icon" style="font-size: 15px;">&#xe63a;</i>评论:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe628;</i>浏览:68
                            </small>
                        </p>
                    </div>
                </div>
                <!--已发布新闻动态-->
                <!--已发布新闻动态-->
                <div class="layui-row" style="margin: 0 0 20px 5%;">
                    <div class="layui-col-space1" ></div>
                    <div class="layui-col-md2" style="height: 100px;">
                        <img src="img/news.png" class="z_photo">
                    </div>
                    <div class="layui-col-md9"style="height: 100px;margin-left: 10px;">
                        <p><strong>展品关键字：***</strong><small>  <i class="layui-icon" style="font-size: 15px; color:#ADADAD;">&#xe60e;</i>    时间：2017-09-14</small></p>
                        <p class="z_intro">简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                        <p class="text-muted">
                            <small>
                                <i class="layui-icon" style="font-size: 15px; ">&#xe650;</i>关注:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c6;</i>点赞：21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c5;</i>踩：21
                                <i class="layui-icon" style="font-size: 15px; ">&#xe600;</i>收藏:20
                                <i class="layui-icon" style="font-size: 15px;">&#xe63a;</i>评论:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe628;</i>浏览:68
                            </small>
                        </p>
                    </div>
                </div>
                <!--已发布新闻动态-->
                <!--已发布新闻动态-->
                <div class="layui-row" style="margin: 0 0 20px 5%;">
                    <div class="layui-col-space1" ></div>
                    <div class="layui-col-md2" style="height: 100px;">
                        <img src="img/news.png" class="z_photo" >
                    </div>
                    <div class="layui-col-md9"style="height: 100px;margin-left: 10px;">
                        <p><strong>展品关键字：***</strong><small>  <i class="layui-icon" style="font-size: 15px; color:#ADADAD;">&#xe60e;</i>    时间：2017-09-14</small></p>
                        <p class="z_intro">简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                        <p class="text-muted">
                            <small>
                                <i class="layui-icon" style="font-size: 15px; ">&#xe650;</i>关注:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c6;</i>点赞：21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c5;</i>踩：21
                                <i class="layui-icon" style="font-size: 15px; ">&#xe600;</i>收藏:20
                                <i class="layui-icon" style="font-size: 15px;">&#xe63a;</i>评论:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe628;</i>浏览:68
                            </small>
                        </p>
                    </div>
                </div>
                <!--已发布新闻动态-->
                <!--已发布新闻动态-->
                <div class="layui-row" style="margin: 0 0 20px 5%;">
                    <div class="layui-col-space1" ></div>
                    <div class="layui-col-md2" style="height: 100px;">
                        <img src="img/news.png" class="z_photo">
                    </div>
                    <div class="layui-col-md9"style="height: 100px;margin-left: 10px;">
                        <p><strong>展品关键字：***</strong><small>  <i class="layui-icon" style="font-size: 15px; color:#ADADAD;">&#xe60e;</i>    时间：2017-09-14</small></p>
                        <p class="z_intro">简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                        <p class="text-muted">
                            <small>
                                <i class="layui-icon" style="font-size: 15px; ">&#xe650;</i>关注:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c6;</i>点赞：21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c5;</i>踩：21
                                <i class="layui-icon" style="font-size: 15px; ">&#xe600;</i>收藏:20
                                <i class="layui-icon" style="font-size: 15px;">&#xe63a;</i>评论:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe628;</i>浏览:68
                            </small>
                        </p>
                    </div>
                </div>
                <!--已发布新闻动态-->
                <!--已发布新闻动态-->
                <div class="layui-row" style="margin: 0 0 20px 5%;">
                    <div class="layui-col-space1" ></div>
                    <div class="layui-col-md2" style="height: 100px;">
                        <img src="img/news.png" class="z_photo" >
                    </div>
                    <div class="layui-col-md9"style="height: 100px;margin-left: 10px;">
                        <p><strong>展品关键字：***</strong><small>  <i class="layui-icon" style="font-size: 15px; color:#ADADAD;">&#xe60e;</i>    时间：2017-09-14</small></p>
                        <p class="z_intro">简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                        <p class="text-muted">
                            <small>
                                <i class="layui-icon" style="font-size: 15px; ">&#xe650;</i>关注:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c6;</i>点赞：21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c5;</i>踩：21
                                <i class="layui-icon" style="font-size: 15px; ">&#xe600;</i>收藏:20
                                <i class="layui-icon" style="font-size: 15px;">&#xe63a;</i>评论:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe628;</i>浏览:68
                            </small>
                        </p>
                    </div>
                </div>
                <!--已发布新闻动态-->
                <!--已发布新闻动态-->
                <div class="layui-row" style="margin: 0 0 20px 5%;">
                    <div class="layui-col-space1" ></div>
                    <div class="layui-col-md2" style="height: 100px;">
                        <img src="img/news.png" class="z_photo">
                    </div>
                    <div class="layui-col-md9"style="height: 100px;margin-left: 10px;">
                        <p><strong>展品关键字：***</strong><small>  <i class="layui-icon" style="font-size: 15px; color:#ADADAD;">&#xe60e;</i>    时间：2017-09-14</small></p>
                        <p class="z_intro">简介：北京时间9月13日凌晨，万众瞩目的苹果
                            3日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷
                            3日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                        3日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷
                        3日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷
                        3日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷
                        3日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷
                        3日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷
                        3日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷
                        3日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷
                        3日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷
                        3日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷
                        3日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷
                        <p class="text-muted">
                            <small>
                                <i class="layui-icon" style="font-size: 15px; ">&#xe650;</i>关注:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c6;</i>点赞：21
                                <i class="layui-icon" style="font-size: 15px;">&#xe6c5;</i>踩：21
                                <i class="layui-icon" style="font-size: 15px; ">&#xe600;</i>收藏:20
                                <i class="layui-icon" style="font-size: 15px;">&#xe63a;</i>评论:21
                                <i class="layui-icon" style="font-size: 15px;">&#xe628;</i>浏览:68
                            </small>
                        </p>
                    </div>
                </div>
                <!--已发布新闻动态-->

            </div>
            <div class='tab-pane fade z_content'>
                <!--产品审核-->
                <table class="layui-table" style="margin: 10px 0 0 5%;width: 90%;">
                    <colgroup>
                        <col width=5%>
                        <col width=10%>
                        <col width=10%>
                        <col width=15%>
                        <col width=55%>
                        <col width=5%>
                    </colgroup>
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>名称</th>
                        <th>价格</th>
                        <th>关键字</th>
                        <th>详情</th>
                        <th>审核</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>01</td>
                        <td>iphone X</td>
                        <td>8000.00元</td>
                        <td>
                            iphone--iphoneX
                        </td>
                        <td>北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</td>
                        <td><input type="checkbox"/></td>
                    </tr>
                    <tr>
                        <td>01</td>
                        <td>iphone X</td>
                        <td>8000.00元</td>
                        <td>
                            iphone--iphoneX
                        </td>
                        <td>北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</td>
                        <td><input type="checkbox"/></td>
                    </tr>
                    <tr>
                        <td>01</td>
                        <td>iphone X</td>
                        <td>8000.00元</td>
                        <td>
                            iphone--iphoneX
                        </td>
                        <td>北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</td>
                        <td><input type="checkbox"/></td>
                    </tr>
                    <tr>
                        <td>01</td>
                        <td>iphone X</td>
                        <td>8000.00元</td>
                        <td>
                            iphone--iphoneX
                        </td>
                        <td>北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</td>
                        <td><input type="checkbox"/></td>
                    </tr>
                    <tr>
                        <td>01</td>
                        <td>iphone X</td>
                        <td>8000.00元</td>
                        <td>
                            iphone--iphoneX
                        </td>
                        <td>北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</td>
                        <td><input type="checkbox"/></td>
                    </tr>
                    <tr>
                        <td>01</td>
                        <td>iphone X</td>
                        <td>8000.00元</td>
                        <td>
                            iphone--iphoneX
                        </td>
                        <td>北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</td>
                        <td><input type="checkbox"/></td>
                    </tr>
                    <tr>
                        <td>01</td>
                        <td>iphone X</td>
                        <td>8000.00元</td>
                        <td>
                            iphone--iphoneX
                        </td>
                        <td>北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</td>
                        <td><input type="checkbox"/></td>
                    </tr>
                    </tbody>
                </table>
                <!--产品审核-->
                <button class="layui-btn layui-btn-normal"  style="width: 150px;margin:10px 0 20px 44%">提交     </button>
            </div>
            <div class='tab-pane fade z_content'>
                <!--管理员审核-->
                <table class="layui-table" style="margin: 10px 0 0 5%;width: 90%;">
                    <colgroup>
                        <col width=5%>
                        <col width=8%>
                        <col width=8%>
                        <col width=10%>
                        <col width=17%>
                        <col width=48%>
                        <col width=5%>
                    </colgroup>
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>姓名</th>
                        <th>身份证号</th>
                        <th>手机号</th>
                        <th>邮箱</th>
                        <th>个人详情</th>
                        <th>审核</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>01</td>
                        <td>张某某</td>
                        <td>123456789009870914</td>
                        <td>12345678909</td>
                        <td>1193097525@qq.com</td>
                        <td>展商的个人经历以及发布产品的个人简介。。。</td>
                        <td><input type="checkbox"/></td>
                    </tr>
                    <tr>
                        <td>01</td>
                        <td>xiaoxin</td>
                        <td>123456789009870914</td>
                        <td>12345678909</td>
                        <td>1193097525@qq.com</td>
                        <td>展商的个人经历以及发布产品的个人简介。。。</td>
                        <td><input type="checkbox"/></td>
                    </tr>
                    <tr>
                        <td>01</td>
                        <td>张某某</td>
                        <td>123456789009870914</td>
                        <td>12345678909</td>
                        <td>1193097525@qq.com</td>
                        <td>展商的个人经历以及发布产品的个人简介。。。</td>
                        <td><input type="checkbox"/></td>
                    </tr>
                    <tr>
                        <td>01</td>
                        <td>张某某</td>
                        <td>123456789009870914</td>
                        <td>12345678909</td>
                        <td>1193097525@qq.com</td>
                        <td>展商的个人经历以及发布产品的个人简介。。。</td>
                        <td><input type="checkbox"/></td>
                    </tr>
                    <tr>
                        <td>01</td>
                        <td>张某某</td>
                        <td>123456789009870914</td>
                        <td>12345678909</td>
                        <td>1193097525@qq.com</td>
                        <td>展商的个人经历以及发布产品的个人简介。。。</td>
                        <td><input type="checkbox"/></td>
                    </tr>
                    <tr>
                        <td>01</td>
                        <td>张某某</td>
                        <td>123456789009870914</td>
                        <td>12345678909</td>
                        <td>1193097525@qq.com</td>
                        <td>展商的个人经历以及发布产品的个人简介。。。</td>
                        <td><input type="checkbox"/></td>
                    </tr>
                    </tbody>
                </table>
                <!--管理员审核-->
                <button class="layui-btn layui-btn-normal"  style="width: 150px;margin:10px 0 20px 44%">提交     </button>
            </div>
            <div class='tab-pane fade z_content' style="margin: 10px 0 0 3%;width: 90%;">
                <a href="${root}/comment/jumpToCommentList">点这里</a>
            </div>
            <div class='tab-pane fade z_content' style="margin: 10px 0 0 3%;width: 90%;">
                会员管理
            </div>
            <div class='tab-pane fade z_content' style="margin: 10px 0 0 3%;width: 90%;">
                系统统计
            </div>
            <div class='tab-pane fade z_content' style="margin: 10px 0 0 3%;width: 90%;">
                系统管理
            </div>
        </div>
        <!-- 内容主体区域 -->
        <div class="layui-footer" style="width: 100%;">
            <p style="padding-left: 40%;">展会管理系统</p>
        </div>
    </div>


    <script src="../tools/layui/layui.js"></script>
    <script>
        //JavaScript代码区域
        layui.use('element', function(){
            var element = layui.element;

        });

        //点击左边右边显示对应内容
        $('.menu li').mouseover(function(){
            idx=$(this).index('.menu li');

            $('.info .z_content').eq(idx).show();
            $('.info .z_content').not($('.info .z_content').eq(idx)).hide();
        });

        //编辑框
        KindEditor.ready(function(K) {
            editor = K.create('#tid'
                ,{
                    resizeType : 2,
                    allowPreviewEmoticons : false,
                    allowImageUpload : true,
                    items : [
                        'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                        'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                        'insertunorderedlist', '|', 'emoticons', 'image', 'link'
                    ],
                }
            );
        });
        //产品审核
        layui.use('table', function(){
            var table = layui.table;

        });
    </script>
</div>
</body>
</html>