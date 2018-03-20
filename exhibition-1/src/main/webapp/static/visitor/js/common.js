function getRootPath() {
    //获取当前网址，如： http://localhost:8088/test/test.jsp返回/test
    var curPath = window.document.location.href;
    //获取主机地址之后的目录，如： test/test.jsp
    var pathName = window.document.location.pathname;
    var pos = curPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8088
    var localhostPaht = curPath.substring(0, pos);
    //获取带"/"的项目名，如：/test
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (projectName);
}

//获取参数方法
function GetUrlParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = encodeURI(window.location.search).substr(1).match(reg);
    if(r!=null)return decodeURI(r[2]); return null;
}

//过滤敏感词
function filter(inputContent) {

    // 多个敏感词，这里直接以数组的形式展示出来
    var arrMg = ["fuck", "tmd", "他妈的","艹","尼玛","你妈","傻逼"];

    // 显示的内容--showContent
    var showContent = inputContent;

    // 正则表达式
    // \d 匹配数字

    for (var i = 0; i < arrMg.length; i++) {

        // 创建一个正则表达式
        var r = new RegExp(arrMg[i], "ig");

        showContent = showContent.replace(r, "*");
    }
    return showContent;
}

/*
过滤特殊字符
 */
function filterStr(s)
{
    var regEn = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im,
        regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;

    if(regEn.test(s) || regCn.test(s)) {
        return false;
    }
    return true;
}

/*
 * 检测对象是否是空对象(不包含任何可读属性)。
 * 方法既检测对象本身的属性，也检测从原型继承的属性(因此没有使hasOwnProperty)。
 */
function isEmpty(obj) {
    for (var name in obj)
    {
        return false;
    }
    return true;
}

function getKeyWord() {
    return 'key';
}

function getType() {
    return "type";
}

function getLoginHtml() {
    var loginHtml='    <div class="container" style="padding-left: 10px;padding-right: 10px;">' +
        '        <form class="form-horizontal" role="form">' +
        '            <div class="form-group">' +
        '                <label for="username" class="col-sm-2 control-label">用户名</label>' +
        '                <div class="col-sm-10">' +
        '                    <input type="text" class="form-control" id="username" placeholder="请输入用户名">' +
        '                </div>' +
        '            </div>' +
        '            <div class="form-group">' +
        '                <label for="password" class="col-sm-2 control-label">密码</label>' +
        '                <div class="col-sm-10">' +
        '                    <input type="password" class="form-control" id="password" placeholder="请输入密码">' +
        '                </div>' +
        '            </div>' +
        '            <div class="form-group">' +
        '                <div class="" align="center">' +
        '                    <a onclick="ajaxLogin();" class="btn btn-primary btn-lg" style="">登录</a>' +
        '                </div>' +
        '            </div>' +
        '        </form>' +
        '' +
        '        <div id="registerDiv" style="padding-top: 20px;">' +
        '            <a href="register.html"><span class="lead">免费注册</span></a>' +
        '        </div>' +
        '    </div>';
    return loginHtml;
}

function getAddAddressHtml() {
    var html='<div style="margin-left: 20px;margin-right: 20px;">' +
        '    <form id="addressForm" class="form" role="form">' +
        '        <div class="form-group">' +
        '            <label class="col-sm-2 control-label" for="prov">省</label>' +
        '            <div class="col-sm-10">' +
        '                <select id="prov" class="prov form-control"></select>' +
        '            </div>' +
        '        </div>' +
        '        <div class="form-group">' +
        '            <label class="col-sm-2 control-label" for="city">市</label>' +
        '            <div class="col-sm-10">' +
        '                <select class="city form-control" id="city" disabled="disabled"></select>' +
        '            </div>' +
        '        </div>' +
        '        <div class="form-group">' +
        '            <label class="col-sm-2 control-label" for="dist">区</label>' +
        '            <div class="col-sm-10">' +
        '                <select class="dist form-control" id="dist" disabled="disabled"></select>' +
        '            </div>' +
        '        </div>' +
        '        <div class="form-group">' +
        '            <label class="col-sm-2 control-label" for="userAdress">详细地址</label>' +
        '            <div class="col-sm-10">' +
        '                <input type="text" class="form-control" name="userAdress" id="userAdress" placeholder="请输入详细地址">' +
        '            </div>' +
        '        </div>' +
        '        <div class="form-group">' +
        '            <label class="col-sm-2 control-label" for="userName">收货人</label>' +
        '            <div class="col-sm-10">' +
        '                <input type="text" class="form-control" name="userName" id="userName" placeholder="请输入收货人姓名">' +
        '            </div>' +
        '        </div>' +
        '        <div class="form-group">' +
        '            <label class="col-sm-2 control-label" for="userPhone">联系电话</label>' +
        '            <div class="col-sm-10">' +
        '                <input type="text" class="form-control" name="userPhone" id="userPhone" placeholder="请输入联系电话">' +
        '            </div>' +
        '        </div>' +
        '        <div class="form-group">' +
        '            <label class="col-sm-2 control-label" for="userZipcode">邮政编码</label>' +
        '            <div class="col-sm-10">' +
        '                <input type="text" class="form-control" name="userZipcode" id="userZipcode" placeholder="请输入邮政编码（不清楚请填000000）">' +
        '            </div>' +
        '        </div>' +
        '        <div align="center"><a href="javascript:void(0);" style="padding-right: 10px;" id="closeBtn" type="submit" class="btn btn-default btn-lg">关闭</a>' +
        '           <a href="javascript:void(0);" style="padding-left: 10px;" id="addAddressBtn" type="submit" class="btn btn-primary btn-lg">提交</a>' +
        '       </div>' +
        '    </form>' +
        '</div>';
    return html;
}
