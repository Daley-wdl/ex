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

/*
 *  设置展商能上传的最大展品数量
 */
function getMaxExhibitsNum() {
    return 100000;
}

/**
 * 获取能上传展品的最大价格
 */
function getMaxPrice() {
    return 1000000;
}

function refreshParent() {
    window.opener.location.href = window.opener.location.href;
    window.close();
}