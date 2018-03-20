<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="UTF-8">
    <title>商品详情</title>
    <link rel="stylesheet" href="layui/css/layui.css" media="all">
    <link rel="stylesheet" href="css/details.css" />
    <link rel="stylesheet" href="font/Hui-font/iconfont.css" />
    <script src="layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="js/holder.min.js" ></script>
</head>
<body>
<div class="content">
    <div class="content1">
        <div class="goodpic">
            <!--展品图片-->
            <div class="layui-carousel" id="test1" lay-filter="test1">
                <div carousel-item="">
                    <div><img src="holder.js/600x280"></div>
                    <div><img src="holder.js/600x280"></div>
                    <div><img src="holder.js/600x280"></div>
                    <div><img src="holder.js/600x280"></div>
                    <div><img src="holder.js/600x280"></div>
                </div>
            </div>
        </div>
        <div class="goodxiangqing">
            <a class="goodtubiao"><i class="Hui-iconfont Hui-iconfont-goods"></i>&nbsp;</a>
            <a class="goodtitle">CK防辐射抗蓝光眼镜女电脑护目镜大框平光镜防辐射抗蓝光眼镜防辐射抗蓝光眼镜</a>
            <div class="goodprice3">
                <div class="goodprice2">¥&nbsp;299.00&nbsp;<i class="Hui-iconfont Hui-iconfont-jiangjia"></i></div>
                <div class="yuexiaoliang"><i class="Hui-iconfont Hui-iconfont-order"></i>&nbsp;销量<i>2000</i>笔</div>
            </div>

            <blockquote class="layui-elem-quote layui-quote-nm">
                <div class="zhanyue">
                    <div class="shangjia"><a href="#" target="_blank"><i class="Hui-iconfont Hui-iconfont-kefu"></i>&nbsp;联系商家</a></div>
                    <div class="pingjia"><a href="#" target="_blank"><i class="Hui-iconfont Hui-iconfont-comment"></i>&nbsp;查看评价</a></div>
                    <div class="zhanqu"><a href="#" target="_blank"><i class="Hui-iconfont Hui-iconfont-huangguan"></i>&nbsp;第<i>&nbsp;二&nbsp;</i>展区</a></div>
                </div>
            </blockquote>


            <div class="goodlianxi">
                <div class="shoucang"><i class="Hui-iconfont Hui-iconfont-card2-kong"></i>&nbsp;<a href="#" target="_blank">加入购物车</a></div>
                <div class="where"><i class="Hui-iconfont Hui-iconfont-android"></i>&nbsp;<a href="#" target="_blank">到这去？ follow me</a></div>
            </div>
        </div>
    </div>

</div>
</body>

<script>
    layui.use(['carousel', 'form'], function(){
        var carousel = layui.carousel
            ,form = layui.form;

        //常规轮播
        carousel.render({
            elem: '#test1'
            ,arrow: 'always'
        });

        //改变下时间间隔、动画类型、高度
        carousel.render({
            elem: '#test2'
            ,interval: 1800
            ,anim: 'fade'
            ,height: '120px'
        });

        //设定各种参数
        var ins3 = carousel.render({
            elem: '#test3'
        });
        //图片轮播
        carousel.render({
            elem: '#test10'
            ,width: '778px'
            ,height: '440px'
            ,interval: 5000
        });

        //事件
        carousel.on('change(test4)', function(res){
            console.log(res)
        });

        var $ = layui.$, active = {
            set: function(othis){
                var THIS = 'layui-bg-normal'
                    ,key = othis.data('key')
                    ,options = {};

                othis.css('background-color', '#5FB878').siblings().removeAttr('style');
                options[key] = othis.data('value');
                ins3.reload(options);
            }
        };

        //监听开关
        form.on('switch(autoplay)', function(){
            ins3.reload({
                autoplay: this.checked
            });
        });

        $('.demoSet').on('keyup', function(){
            var value = this.value
                ,options = {};
            if(!/^\d+$/.test(value)) return;

            options[this.name] = value;
            ins3.reload(options);
        });

        //其它示例
        $('.demoTest .layui-btn').on('click', function(){
            var othis = $(this), type = othis.data('type');
            active[type] ? active[type].call(this, othis) : '';
        });
    });
</script>
</html>
