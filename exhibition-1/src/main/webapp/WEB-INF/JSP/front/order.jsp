<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="UTF-8">
    <title>订单页面</title>
    <link rel="stylesheet" href="font/Hui-font/iconfont.css" />
    <link rel="stylesheet" href="layui/css/layui.css" media="all">
    <link rel="stylesheet" href="css/dingdan.css" />
    <script src="layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="js/holder.min.js" ></script>
</head>
<body>
<div class="con1">
    <div class="con2">
        <div class="fie">
            <fieldset class="layui-elem-field layui-field-title">
                <legend>全部订单</legend>
            </fieldset>
        </div>
        <div class="layui-col-xs12 layui-col-md9">
            <div class="panel panel-default">
                <div class="panel-header">
                    <!--订单标题:日期 订单号 店铺名称 联系商家     删除订单（弹框：确定删除？）-->
                    <div class="checked">
                        <label>
                            <input type="checkbox" id="checked" name="checked" value="checked" />
                        </label>
                    </div>

                    <div id="riqi">
                        日期：2017-01-01
                    </div>
                    <div id="dingdanhao">
                        订单号：88888888
                    </div>
                    <div class="mingc">
                        <a href="#">xxxx店铺</a>
                    </div>
                    <div class="lxsj">
                        <i class="Hui-iconfont Hui-iconfont-kefu">&nbsp;</i><a href="#">联系商家</a>
                    </div>
                    <div class="delete" style="float: right;">
                        <div class="site-demo-button" id="layerDemo" style="margin-bottom: 0;">
                            <button data-method="confirmTrans" class="layui-btn">删除订单</button>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <!--订单内容：图片  商品名称 数量  价格 订单状态-->
                    <div class="ddimg">
                        <img src="holder.js/150x150">
                    </div>
                    <div class="ddname">
                        <div class="dianpu"><a href="#">华为p10 4GB+128GB 钻雕金 全网通</a></div>
                        <div class="dianming">颜色分类：<a href="#">黑色<br />128G内存  4G运行 </a></div>
                    </div>
                    <div class="ddnum">
                        X10
                    </div>
                    <div class="ddprice">
                        ￥33690.00
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-col-xs12 layui-col-md9">
            <div class="panel panel-default">
                <div class="panel-header">
                    <!--订单标题:日期 订单号 店铺名称 联系商家     删除订单（弹框：确定删除？）-->
                    <div class="checked">
                        <label>
                            <input type="checkbox" id="checked" name="checked" value="checked" />
                        </label>
                    </div>

                    <div id="riqi">
                        日期：2017-01-01
                    </div>
                    <div id="dingdanhao">
                        订单号：88888888
                    </div>
                    <div class="mingc">
                        <a href="#">xxxx店铺</a>
                    </div>
                    <div class="lxsj">
                        <i class="Hui-iconfont Hui-iconfont-kefu">&nbsp;</i><a href="#">联系商家</a>
                    </div>
                    <div class="delete" style="float: right;">
                        <div class="site-demo-button" id="layerDemo" style="margin-bottom: 0;">
                            <button data-method="confirmTrans" class="layui-btn">删除订单</button>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <!--订单内容：图片  商品名称 数量  价格 订单状态-->
                    <div class="ddimg">
                        <img src="holder.js/150x150">
                    </div>
                    <div class="ddname">
                        <div class="dianpu"><a href="#">华为p10 4GB+128GB 钻雕金 全网通</a></div>
                        <div class="dianming">颜色分类：<a href="#">黑色<br />128G内存  4G运行 </a></div>
                    </div>
                    <div class="ddnum">
                        X10
                    </div>
                    <div class="ddprice">
                        ￥33690.00
                    </div>
                </div>
            </div>
        </div>


        <div class="layui-col-xs12 layui-col-md9">
            <div class="panel panel-default">
                <div class="panel-header">
                    <!--订单标题:日期 订单号 店铺名称 联系商家     删除订单（弹框：确定删除？）-->
                    <div class="checked">
                        <label>
                            <input type="checkbox" id="checked" name="checked" value="checked" />
                        </label>
                    </div>

                    <div id="riqi">
                        日期：2017-01-01
                    </div>
                    <div id="dingdanhao">
                        订单号：88888888
                    </div>
                    <div class="mingc">
                        <a href="#">xxxx店铺</a>
                    </div>
                    <div class="lxsj">
                        <i class="Hui-iconfont Hui-iconfont-kefu">&nbsp;</i><a href="#">联系商家</a>
                    </div>
                    <div class="delete" style="float: right;">
                        <div class="site-demo-button" id="layerDemo" style="margin-bottom: 0;">
                            <button data-method="confirmTrans" class="layui-btn">删除订单</button>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <!--订单内容：图片  商品名称 数量  价格 订单状态-->
                    <div class="ddimg">
                        <img src="holder.js/150x150">
                    </div>
                    <div class="ddname">
                        <div class="dianpu"><a href="#">华为p10 4GB+128GB 钻雕金 全网通</a></div>
                        <div class="dianming">颜色分类：<a href="#">黑色<br />128G内存  4G运行 </a></div>
                    </div>
                    <div class="ddnum">
                        X10
                    </div>
                    <div class="ddprice">
                        ￥33690.00
                    </div>
                </div>
            </div>
        </div>


        <div class="layui-col-xs12 layui-col-md9">
            <div class="panel panel-default">
                <div class="panel-header">
                    <!--订单标题:日期 订单号 店铺名称 联系商家     删除订单（弹框：确定删除？）-->
                    <div class="checked">
                        <label>
                            <input type="checkbox" id="checked" name="checked" value="checked" />
                        </label>
                    </div>

                    <div id="riqi">
                        日期：2017-01-01
                    </div>
                    <div id="dingdanhao">
                        订单号：88888888
                    </div>
                    <div class="mingc">
                        <a href="#">xxxx店铺</a>
                    </div>
                    <div class="lxsj">
                        <i class="Hui-iconfont Hui-iconfont-kefu">&nbsp;</i><a href="#">联系商家</a>
                    </div>
                    <div class="delete" style="float: right;">
                        <div class="site-demo-button" id="layerDemo" style="margin-bottom: 0;">
                            <button data-method="confirmTrans" class="layui-btn">删除订单</button>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <!--订单内容：图片  商品名称 数量  价格 订单状态-->
                    <div class="ddimg">
                        <img src="holder.js/150x150">
                    </div>
                    <div class="ddname">
                        <div class="dianpu"><a href="#">华为p10 4GB+128GB 钻雕金 全网通</a></div>
                        <div class="dianming">颜色分类：<a href="#">黑色<br />128G内存  4G运行 </a></div>
                    </div>
                    <div class="ddnum">
                        X10
                    </div>
                    <div class="ddprice">
                        ￥33690.00
                    </div>
                </div>
            </div>
        </div>




        <!--分页-->
        <div class="fenye"><div id="demo7"></div></div>

    </div>
</div>

<script>
    layui.use(['laypage', 'layer'], function(){
        var laypage = layui.laypage
            ,layer = layui.layer;
        //完整功能
        laypage.render({
            elem: 'demo7'
            ,count: 100
            ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
            ,jump: function(obj){
                console.log(obj)
            }
        });
    });
    layui.use(['element', 'layer'], function(){
        var element = layui.element;
        var layer = layui.layer;

        //监听折叠
        element.on('collapse(test)', function(data){
            layer.msg('展开状态：'+ data.show);
        });
    });


    layui.use('layer', function(){ //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句

        //触发事件
        var active = {
            confirmTrans: function(){
                //配置一个透明的询问框
                layer.msg('确定删除该订单吗？', {
                    time: 20000, //20s后自动关闭
                    btn: ['确定删除', '不了，谢谢']
                });
            }
        };

        $('#layerDemo .layui-btn').on('click', function(){
            var othis = $(this), method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });

    });
</script>

</body>
</html>
