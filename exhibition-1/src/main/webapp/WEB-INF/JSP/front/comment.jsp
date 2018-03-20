<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>展会智能管理系统</title>
    <link rel="stylesheet" href="../tools/layui/css/layui.css">
    <link rel="stylesheet" href="../css/index.css"/>
    <link rel="stylesheet" href="../css/comment.css"/>
    <script src="../js/jquery.js"></script>
<body>
<!--<div class="layui-container ">
    <div class="z_search">
         <input type="text"placeholder="搜索" autocomplete="off" class="layui-input">
    </div>

</div>-->
<div class="jd_laylout">
    <header class="jd_header">
        <div class="jd_header_box"><!--把所有的内容放在一个父容器里面-->
            <div style="height: 8px;width: 100%;"></div>
            <!--<input type="text"placeholder="搜索" autocomplete="off" class="layui-input z_input">-->
            <i class="layui-icon" style="font-size: 25px; color: #F5F5F;margin-left: 2%;">&#xe603;</i>
            <div style="height: 2px;width: 100%;"></div>
        </div>
    </header>
</div>
<div class="z_blank"></div>
<div class="z_btn">
    <button class="layui-btn layui-btn-mini layui-btn-radius" style="background:orangered ;">全部(21)</button>
    <button class="layui-btn layui-btn-mini layui-btn-radius" style="background:#F2DEDE;color: black;">好评(18)</button>
    <button class="layui-btn layui-btn-mini layui-btn-radius" style="background:#F2DEDE;color: black;">差评(3)</button>
</div>


<!--评论-->
<div>
    <div class="layui-row" style="margin: 0 0 20px 5%;">
        <div class="layui-col-md1">
            <img src="../img/tx.jpg" class="z_myphoto">
        </div>
        <div class="layui-col-md10">
            <strong style="font-size: 14px;">游客账号</strong>
            <small class="z_time">2017-09-14</small>
            <p class="z_detail">
                <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
            </p>
        </div>
        <div class="layui-col-space1">
        </div>
        <div class="layui-row">
            <div class="" style="margin-left: 10%;margin-right: 3%;">
                <p class="z_intro">
                    简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                <p class="text-muted">
                    <small>
                        <span class="z_time" style="float: left;">浏览:68次</span>
                        <span class="z_time"
                              style="border: 1px solid #ADADAD;padding-right: 5px;border-radius:10px;font-size: 11px;"><i
                                class="layui-icon" style="font-size: 12px;">&#xe6c6;</i>点赞   </span>
                        <span id="test2" class="z_time test2"
                              style="border: 1px solid #ADADAD;padding-right: 5px;border-radius:10px;font-size: 11px;"><i
                                class="layui-icon" style="font-size: 12px;">&#xe63a;</i>   评论   </span>
                    </small>
                </p>

            </div>
        </div>
    </div>
    <!--评论-->


    <hr/>
    <!--评论-->
    <div>
        <div class="layui-row" style="margin: 0 0 20px 5%;">
            <div class="layui-col-md1">
                <img src="../img/tx.jpg" class="z_myphoto">
            </div>
            <div class="layui-col-md10">
                <strong style="font-size: 14px;">游客账号</strong>
                <small class="z_time">2017-09-14</small>
                <p class="z_detail">
                    <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                    <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                    <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                    <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                </p>
            </div>
            <div class="layui-col-space1">
            </div>
            <div class="layui-row">
                <div class="" style="margin-left: 10%;margin-right: 3%;">
                    <p class="z_intro">
                        简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                    <p class="text-muted">
                        <small>
                            <span class="z_time" style="float: left;">浏览:68次</span>
                            <span class="z_time"
                                  style=" border: 1px solid #ADADAD;padding-right: 5px;border-radius:10px;font-size: 11px;"><i
                                    class="layui-icon" style="font-size: 12px;">&#xe6c6;</i>点赞   </span>
                            <span id="test2" class="z_time test2"
                                  style="border: 1px solid #ADADAD;padding-right: 5px;border-radius:10px;font-size: 11px;"><i
                                    class="layui-icon" style="font-size: 12px;">&#xe63a;</i>   评论   </span>
                        </small>
                    </p>

                </div>
            </div>
        </div>
        <!--评论-->
        <hr/>
        <!--评论-->
        <div>
            <div class="layui-row" style="margin: 0 0 20px 5%;">
                <div class="layui-col-md1">
                    <img src="../img/tx.jpg" class="z_myphoto">
                </div>
                <div class="layui-col-md10">
                    <strong style="font-size: 14px;">游客账号</strong>
                    <small class="z_time">2017-09-14</small>
                    <p class="z_detail">
                        <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                        <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                        <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                        <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                    </p>
                </div>
                <div class="layui-col-space1">
                </div>
                <div class="layui-row">
                    <div class="" style="margin-left: 10%;margin-right: 3%;">
                        <p class="z_intro">
                            简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                        <p class="text-muted">
                            <small>
                                <span class="z_time" style="float: left;">浏览:68次</span>
                                <span class="z_time"
                                      style=" border: 1px solid #ADADAD;padding-right: 5px;border-radius:10px;font-size: 11px;"><i
                                        class="layui-icon" style="font-size: 12px;">&#xe6c6;</i>点赞   </span>
                                <span id="test2" class="z_time test2"
                                      style="border: 1px solid #ADADAD;padding-right: 5px;border-radius:10px;font-size: 11px;"><i
                                        class="layui-icon" style="font-size: 12px;">&#xe63a;</i>   评论   </span>
                            </small>
                        </p>

                    </div>
                </div>
            </div>
            <!--评论-->
            <hr/>
            <!--评论-->
            <div>
                <div class="layui-row" style="margin: 0 0 20px 5%;">
                    <div class="layui-col-md1">
                        <img src="../img/tx.jpg" class="z_myphoto">
                    </div>
                    <div class="layui-col-md10">
                        <strong style="font-size: 14px;">游客账号</strong>
                        <small class="z_time">2017-09-14</small>
                        <p class="z_detail">
                            <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                            <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                            <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                            <i class="layui-icon" style="font-size: 15px; color: darkorange;">&#xe658;</i>
                        </p>
                    </div>
                    <div class="layui-col-space1">
                    </div>
                    <div class="layui-row">
                        <div class="" style="margin-left: 10%;margin-right: 3%;">
                            <p class="z_intro">
                                简介：北京时间9月13日凌晨，万众瞩目的苹果秋季新品发布会终于在乔布斯剧院拉开了帷幕，库克一口气发布了足足5款产品大大超出我们之前的预料。当然，我们最关注的依然是苹果安身立命的产品线：iPhone。</p>
                            <p class="text-muted">
                                <small>
                                    <span class="z_time" style="float: left;">浏览:68次</span>
                                    <span class="z_time"
                                          style=" border: 1px solid #ADADAD;padding-right: 5px;border-radius:10px;font-size: 11px;"><i
                                            class="layui-icon" style="font-size: 12px;">&#xe6c6;</i>点赞   </span>
                                    <span id="test2" class="z_time test2"
                                          style="border: 1px solid #ADADAD;padding-right: 5px;border-radius:10px;font-size: 11px;"><i
                                            class="layui-icon" style="font-size: 12px;">&#xe63a;</i>   评论   </span>
                                </small>
                            </p>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
                <button onclick="getCommentList()"> 测试</button>


    <!--评论-->
    <script src="../tools/layui/layui.js"></script>
    <script src="../tools/lib/jquery/1.9.1/jquery.min.js"></script>
    <script src="../tools/lib/layer/2.4/layer.js"></script>
    <script>
        //弹出一个页面层 text = document.getElementsByClassName("")
        $('.test2').on('click', function () {
            layer.open({
                type: 1,
                title: '评论',
                skin: 'layui-layer-rim', //加上边框
                area: ['80%', '230px'], //宽高
                content: '<textarea placeholder="请输入内容" class="layui-textarea" style="width: 90%; margin:0 auto;margin-top:10px"></textarea><button class="layui-btn layui-btn-normal layui-layer-close" style="margin:10px 0 0 35%;min-width:90px;width:10%">提交</button>'
            });
        });

        var page = 0;

        function getCommentList() {
            $.ajax({
                type : 'get',
                url : '${root}/comment/commentListJson',
                dataType : 'json',
                data : {
                    page : page
                },
                success : function(result) {
                    alert(result.reason);
                    //处理数据操作
                },
                error : function(error) {
                    alert(error);
                }
            });
        }

        //下一页
        function nextPage() {

            //判断是否是最后一页
            page = page + 1;
            $.ajax({
                type : 'get',
                url : '${root}/comment/commentListJson',
                data : {
                    page : page
                },
                dataType : 'json',
                success : function(result) {
                    alert(result.reason);
                },
                error : function(error) {
                    alert('cuole');
                }
            });
        }
    </script>
</body>
</html>
