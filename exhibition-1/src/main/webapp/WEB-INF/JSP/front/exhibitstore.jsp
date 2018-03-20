<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <!--bs框架-->
    <!--<link rel="stylesheet" href="bs/css/bootstrap.min.css">-->
    <script src="bs/js/jquery.min.js"></script>
    <script src="bs/js/bootstrap.min.js"></script>
    <!--其他框架-->
    <link rel="stylesheet" href="font/Hui-font/iconfont.css" />
    <link rel="stylesheet" href="font/iconfont.css" />
    <link rel="stylesheet" href="font/font_hn/iconfont.css" />
    <link rel="stylesheet" href="layui/css/layui.css" media="all">
    <script src="layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="js/holder.min.js" ></script>

    <link rel="stylesheet" href="css/allgoods.css" />
</head>

<body data-spy="scroll" data-target=".navbar-example" data-offset="20">
<!--<div class="container">-->
<div class="all2">
    <div class="all3">
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

        <!--左边导航-->
        <div class="left-list">
            <ul class="layui-nav layui-nav-tree layui-inline nav nav-tabs" lay-filter="demo" style="margin-right: 10px;">
                <li class="allpieces" style="height: 40px; width:200px; color: #009688; font-family:'楷体';line-height: 40px;font-size: 18px; font-weight: bold;border-bottom: #92B8B1 solid 1px;">&nbsp;<span class="Hui-iconfont Hui-iconfont-fenlei"></span>&nbsp;展区一所有商品</li>
                <li class="layui-nav-item layui-nav-itemed">
                    <a href="#meishi" style="color: #009688;"><i class="iconfont icon-shipin"></i>&nbsp;汇吃美食</a>
                    <dl class="layui-nav-child">
                        <dd><a href="#lingshi" target="_blank"><span class="Hui-iconfont Hui-iconfont-like"></span>&nbsp;休闲零食</a></dd>
                        <dd><a href="#xianrou" target="_blank"><span class="Hui-iconfont Hui-iconfont-like"></span>&nbsp;水产鲜肉</a></dd>
                        <dd><a href="#mincha" target="_blank"><span class="Hui-iconfont Hui-iconfont-like"></span>&nbsp;四季茗茶</a></dd>
                        <dd><a href="#guoshu" target="_blank"><span class="Hui-iconfont Hui-iconfont-like"></span>&nbsp;生鲜果蔬</a></dd>
                        <dd><a href="#jianiang" target="_blank"><span class="Hui-iconfont Hui-iconfont-like"></span>&nbsp;美酒佳酿</a></dd>
                        <dd><a href="#yinliao" target="_blank"><span class="Hui-iconfont Hui-iconfont-like"></span>&nbsp;牛奶饮料</a></dd>
                        <dd><a href="#yangsheng" target="_blank"><span class="Hui-iconfont Hui-iconfont-like"></span>&nbsp;滋补养生</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="#svn" style="color: #009688;"><span class="Hui-iconfont Hui-iconfont-pifu"></span>&nbsp;珠宝服饰</a>
                    <dl class="layui-nav-child">
                        <dd><a href="#nvzhuang" target="_blank"><span class="Hui-iconfont Hui-iconfont-hot"></span>&nbsp;女装</a></dd>
                        <dd><a href="#nanzhuang" target="_blank"><span class="Hui-iconfont Hui-iconfont-hot"></span>&nbsp;男装</a></dd>
                        <dd><a href="#tongzhuang" target="_blank"><span class="Hui-iconfont Hui-iconfont-hot"></span>&nbsp;童装</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="#sql" style="color: #009688;"><i class="iconfont icon-huaniao"></i>&nbsp;花鸟文娱</a>
                    <dl class="layui-nav-child">
                        <dd><a href="#lvzhi" target="_blank"><span class="Hui-iconfont Hui-iconfont-tianqi-qing"></span>&nbsp;花卉绿植</a></dd>
                        <dd><a href="#yongpin" target="_blank"><span class="Hui-iconfont Hui-iconfont-tianqi-qing"></span>&nbsp;园艺用品</a></dd>
                        <dd><a href="#menggou" target="_blank"><span class="Hui-iconfont Hui-iconfont-tianqi-qing"></span>&nbsp;萌狗世界</a></dd>
                        <dd><a href="#maomi" target="_blank"><span class="Hui-iconfont Hui-iconfont-tianqi-qing"></span>&nbsp;猫咪世界</a></dd>
                        <dd><a href="#xiyang" target="_blank"><span class="Hui-iconfont Hui-iconfont-tianqi-qing"></span>&nbsp;西洋乐器</a></dd>
                        <dd><a href="#minzu" target="_blank"><span class="Hui-iconfont Hui-iconfont-tianqi-qing"></span>&nbsp;民族乐器</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item" ><a href="#html" target="_blank" style="color: #009688;"><i class="iconfont icon-gengduo"></i>&nbsp;其他</a></li>
            </ul>
        </div>

        <!--中间类详情-->
        <div class="mid-sort">
            <!--美食-->
            <blockquote class="layui-elem-quote" id="meishi" style="margin-top: 20px; font-size: 20px;"><i class="iconfont icon-shipin"></i>&nbsp;汇吃美食</blockquote>
            <!--休闲零食-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="lingshi"></span>&nbsp;休闲零食</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <!--水产鲜肉-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="xianrou"></span>&nbsp;水产鲜肉</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <!--四季茗茶-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="mincha"></span>&nbsp;四季茗茶</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <!--生鲜果蔬-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="guoshu"></span>&nbsp;生鲜果蔬</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <!--美酒佳酿-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="jianiang"></span>&nbsp;美酒佳酿</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <!--牛奶饮料-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="yinliao"></span>&nbsp;牛奶饮料</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <!--滋补养生-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="yangsheng"></span>&nbsp;滋补养生</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>

            <!--珠宝服饰-->
            <blockquote class="layui-elem-quote" id="svn" style="margin-top: 20px; font-size: 20px;"><span class="Hui-iconfont Hui-iconfont-pifu"></span>&nbsp;珠宝服饰</blockquote>
            <!--女装-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="nvzhuang"></span>&nbsp;女装</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <!--男装-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="nanzhuang"></span>&nbsp;男装</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <!--童装-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="tongzhuang"></span>&nbsp;童装</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>



            <!--花鸟文娱-->
            <blockquote class="layui-elem-quote" id="sql" style="margin-top: 20px; font-size: 20px;"><i class="iconfont icon-huaniao"></i>&nbsp;花鸟文娱</blockquote>
            <!--花卉-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="lvzhi"></span>&nbsp;花卉绿植</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <!--园艺用品-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="yongpin"></span>&nbsp;园艺用品</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <!--萌狗世界-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="menggou"></span>&nbsp;萌狗世界</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <!--猫咪世界-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="maomi"></span>&nbsp;猫咪世界</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <!--西洋乐器-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="xiyang"></span>&nbsp;西洋乐器</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <!--民族乐器-->
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><span class="Hui-iconfont Hui-iconfont-like" id="minzu"></span>&nbsp;民族乐器</legend>
            </fieldset>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>


            <!--其他-->
            <blockquote class="layui-elem-quote" id="html" style="margin-top: 20px; font-size: 20px;"><i class="iconfont icon-gengduo"></i>&nbsp;其他</blockquote>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
            <div cl class="layui-row">
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="layui-col-md3">
                    <fieldset class="layui-elem-field">
                        <legend><i class="layui-icon" style="font-size: 22px;color: #009688;font-weight: bold;">&#xe609;</i> </legend>
                        <div class="layui-field-box">
                            <a href="#" target="_blank"><li> <img src="holder.js/200x200"></li></a>
                            <div class="gooddetails">
                                <i class="goodprice">¥299.00</i>
                                <div class="gooditem"><a href="#" target="_blank">婴儿连体衣服宝宝外出服寝居服饰春秋装</a></div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>

        </div>
        <!--页脚-->

    </div>
    <footer class="footer mt-20">
        <div class="container-fluid">
            <nav> <a href="#" target="_blank">关于我们</a> <span class="pipe">|</span> <a href="#" target="_blank">联系我们</a> <span class="pipe">|</span> <a href="#" target="_blank">法律声明</a> </nav>
            <p>Copyright &copy;2017 展会  All Rights Reserved. <br>
                <a href="http://www.miitbeian.gov.cn/" target="_blank" rel="nofollow">鄂ICP备0000000号</a><br>
            </p>
        </div>
    </footer>
</div>
<!--</div>-->
<div class="nav1"></div>
</body>
<script>
    $('li a').bind('click', function(e) {
        e.preventDefault();
        $('html,body').animate({
            scrollTop: $(this.hash).offset().top - $('.nav1').height()
        }, 500);
        console.log($(this.hash))
    });


    layui.use('element', function(){
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

        //监听导航点击
        element.on('nav(demo)', function(elem){
            layer.msg(elem.text());
        });
    });

</script>
</html>
