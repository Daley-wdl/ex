<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="UTF-8">
    <title>展会首页</title>
    <link rel="stylesheet" href="layui/css/layui.css" media="all">
    <link rel="stylesheet" href="css/main.css" />
    <link rel="stylesheet" href="font/iconfont.css" />
    <link rel="stylesheet" href="font/Hui-font/iconfont.css" />
    <script src="layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="js/holder.min.js" ></script>
    <script type="text/javascript" src="js/jquery.min.js" ></script>
    <script type="text/javascript" src="js/jcarousellite.js" ></script>
</head>
<body>

<div class="first">
    <div class="all">
        <div class="nav">
            <ul class="layui-nav">
                <li class="layui-nav-item">
                    <a href="">首页</a>
                </li>
                <li class="layui-nav-item">
                    <a href="">消息<span class="layui-badge">9</span></a>
                </li>
                <li class="layui-nav-item">
                    <a href="">个人中心<span class="layui-badge-dot"></span></a>
                </li>
                <li class="layui-nav-item" style="float: right;margin-right: 20px;">
                    <a href="javascript:;"><img src="http://t.cn/RCzsdCq" class="layui-nav-img">我</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">修改信息</a></dd>
                        <dd><a href="javascript:;">安全管理</a></dd>
                        <dd><a href="javascript:;">退出</a></dd>
                    </dl>
                </li>
            </ul>
        </div>
        <!--搜索框-->
        <div class="layui-col-xs12 layui-col-md8">
            <div class="sousuo">
                <div class="search-top">

                    <form method="get" onsubmit="">
                        <div class="search">
                            <i class="iconfont icon-sousuo"></i>
                            <input type="text" class="search-keyword" placeholder="请输入商品名称"/>
                            <input type="submit" class="search-btn" value="搜索"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!--轮播图-->
        <div class="layui-carousel" id="test10">
            <div carousel-item="">
                <div><img src="img/1.jpg"></div>
                <div><img src="img/2.jpg"></div>
                <div><img src="img/3.jpg"></div>
            </div>
        </div>

        <!--通知公告-->
        <div class="layui-collapse" lay-accordion="">
            <div class="layui-colla-item">
                <h2 class="layui-colla-title">XX店铺公告</h2>
                <div class="layui-colla-content layui-show">
                    <div id="marquee" class="marquee">
                        <div id="holder" style="font-size: 15px;">
                            <ul>
                                <li><b>[公告一]</b>你只有忍过别人忍不了的，才能得到别人得不到的。你只有忍过别人忍不了的，才能得到别人得不到的。你只有忍过别人忍不了的，才能得到别人得不到的。你只有忍过别人忍不了的，才能得到别人得不到的。你只有忍过别人忍不了的，才能得到别人得不到的。你只有忍过别人忍不了的，才能得到别人得不到的。你只有忍过别人忍不了的，才能得到别人得不到的。你只有忍过别人忍不了的，才能得到别人得不到的。你只有忍过别人忍不了的，才能得到别人得不到的。</li><br />
                                <li><b>[公告二]</b>名利二字太小，绝非先生格局。名利二字太小，绝非先生格局。名利二字太小，绝非先生格局。名利二字太小，绝非先生格局。名利二字太小，绝非先生格局。名利二字太小，绝非先生格局。名利二字太小，绝非先生格局。名利二字太小，绝非先生格局。名利二字太小，绝非先生格局。名利二字太小，绝非先生格局。名利二字太小，绝非先生格局。</li><br />
                                <li><b>[公告三]</b>脚踏实地谋发展，努力努力再努力。脚踏实地谋发展，努力努力再努力。脚踏实地谋发展，努力努力再努力。脚踏实地谋发展，努力努力再努力。脚踏实地谋发展，努力努力再努力。脚踏实地谋发展，努力努力再努力。脚踏实地谋发展，努力努力再努力。脚踏实地谋发展，努力努力再努力。脚踏实地谋发展，努力努力再努力。</li><br />
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--产品推荐-->
        <blockquote class="layui-elem-quote" style="margin-top: 20px; font-size: 20px;"><i class="Hui-iconfont">&#xe6bb;</i>&nbsp;产品推荐</blockquote>

        <div class="rollpic cl">
            <a href="javascript:void(0)" class="prev"></a>
            <div class="rollpicshow">
                <ul>
                    <a href="#" target="_blank"><li> <img src="holder.js/230x230"></li></a>
                    <a href="#" target="_blank"><li> <img src="holder.js/230x230"></li></a>
                    <a href="#" target="_blank"><li> <img src="holder.js/230x230"></li></a>
                    <a href="#" target="_blank"><li> <img src="holder.js/230x230"></li></a>
                    <a href="#" target="_blank"><li> <img src="holder.js/230x230"></li></a>
                    <a href="#" target="_blank"><li> <img src="holder.js/230x230"></li></a>
                    <a href="#" target="_blank"><li> <img src="holder.js/230x230"></li></a>
                    <a href="#" target="_blank"><li> <img src="holder.js/230x230"></li></a>
                </ul>
            </div>
            <a href="javascript:void(0)" class="next"></a>
        </div>

        <!--展区1,2,3-->
        <blockquote class="layui-elem-quote" style="margin-top: 20px; font-size: 20px;"><i class="Hui-iconfont Hui-iconfont-goods"></i>&nbsp;展区一<a class="more">>>更多商品</a></blockquote>
        <div cl class="layui-row">
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
        </div>

        <blockquote class="layui-elem-quote" style="margin-top: 20px; font-size: 20px;"><i class="Hui-iconfont Hui-iconfont-goods"></i>&nbsp;展区二<a class="more">>>更多商品</a></blockquote>
        <div cl class="layui-row">
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
        </div>

        <blockquote class="layui-elem-quote" style="margin-top: 20px; font-size: 20px;"><i class="Hui-iconfont Hui-iconfont-goods"></i>&nbsp;展区三<a class="more">>>更多商品</a></blockquote>
        <div cl class="layui-row">
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
        </div>

        <blockquote class="layui-elem-quote" style="margin-top: 20px; font-size: 20px;"><i class="Hui-iconfont Hui-iconfont-goods"></i>&nbsp;展区四<a class="more">>>更多商品</a></blockquote>
        <div cl class="layui-row">
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
            <div class="layui-col-md3">
                <fieldset class="layui-elem-field">
                    <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                    <div class="layui-field-box">
                        <a href="#" target="_blank"><li> <img src="holder.js/235x230"></li></a>
                    </div>
                </fieldset>
            </div>
        </div>



    </div>
    <!--页脚-->
    <footer class="footer mt-20">
        <div class="container-fluid">
            <nav> <a href="#" target="_blank">关于我们</a> <span class="pipe">|</span> <a href="#" target="_blank">联系我们</a> <span class="pipe">|</span> <a href="#" target="_blank">法律声明</a> </nav>
            <p>Copyright &copy;2017 展会  All Rights Reserved. <br>
                <a href="http://www.miitbeian.gov.cn/" target="_blank" rel="nofollow">鄂ICP备0000000号</a><br>
            </p>
        </div>
    </footer>
</div>
</body>
<script>
    layui.use('element', function(){
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

        //监听导航点击
        element.on('nav(demo)', function(elem){
            //console.log(elem)
            layer.msg(elem.text());
        });
    });

    layui.use(['carousel', 'form'], function(){
        var carousel = layui.carousel
            ,form = layui.form;


        //图片轮播
        carousel.render({
            elem: '#test10'
            ,width: '1100px'
            ,height: '300px'
            ,interval: 5000
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

    //公告轮播
    !function ($) {
        $.Huimarquee = function(height,speed,delay){
            var scrollT;
            var pause = false;
            var ScrollBox = document.getElementById("marquee");
            if(document.getElementById("holder").offsetHeight <= height) return;
            var _tmp = ScrollBox.innerHTML.replace('holder', 'holder2')
            ScrollBox.innerHTML += _tmp;
            ScrollBox.onmouseover = function(){pause = true}
            ScrollBox.onmouseout = function(){pause = false}
            ScrollBox.scrollTop = 0;
            var start = function(){
                scrollT = setInterval(scrolling,speed);
                if(!pause) ScrollBox.scrollTop += 2}
            var scrolling = function(){
                if(ScrollBox.scrollTop % height != 0){
                    ScrollBox.scrollTop += 2;
                    if(ScrollBox.scrollTop >= ScrollBox.scrollHeight/2) ScrollBox.scrollTop = 0}
                else{
                    clearInterval(scrollT);
                    setTimeout(start,delay)}
            }
            setTimeout(start,delay)}
    }(window.jQuery);
    $.Huimarquee(60,100,10);/*移动高度,移动速度,间隔时间*/

    //推荐轮播
    $(function(){
        $(".rollpicshow").jCarouselLite({
            auto: 2000, /*自动播放间隔时间*/
            speed: 500, /*速度*/
            btnNext:".next",/*向前滚动*/
            btnPrev:".prev",/*向后滚动*/
            visible:4 /*显示数量*/
        })});
</script>
</html>
