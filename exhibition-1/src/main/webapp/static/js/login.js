$(document).ready(function () {
    //设置验证码
    document.getElementById("vaildImg").src=getRootPath() + "/common/getVaild";

    //检查是否有上次登录的错误信息，如果有，就显示
    // var errorMsg = getParam("error");
    var errorMsg = GetUrlParam("error");
    if (errorMsg!=null) {
        alert(decodeURI(errorMsg));
    }


    $("#loginform").attr("action", getRootPath() + "/common/doLogin");//表单提交路径
    $("#btns").click(function () {
            $("#loginform").submit();
    });

//$('#btns').click();
// 表单验证
    $('input[name=username]').blur(function () {
        val = this.value;

        if (val==null||val.length>12) {
            //if(!val.match(/^[1-9]{1}\d{10,16}$/)){
            $(this).data({'s': 0});
            $(this).next().show();
            $(this).next().next().hide();
        } else {
            $(this).data({'s': 1});
            $(this).next().hide();
            $(this).next().next().show();
        }
    });
    $('input[name=password]').blur(function () {
        val = this.value;

        if (!val.match(/^[u0391-uFFE5w]+$/)) {
            $(this).data({'s': 0});
            $(this).next().show();
            $(this).next().next().hide();
        } else {
            $(this).data({'s': 1});
            $(this).next().hide();
            $(this).next().next().show();
        }
    });

    $('input[name=yzm]').blur(function () {
        if (IsBy == false) {
            $(this).data({'s': 0});
            $(this).next().next().show();
            $(this).next().next().next().hide();
        } else {
            $(this).data({'s': 1});
            $(this).next().next().hide();
            $(this).next().next().next().show();
        }
    });
    // $('form').submit(function () {
    //     $('.auth').blur();
    //
    //     tot = 0;
    //
    //     $('.auth').each(function () {
    //         tot += $(this).data('s');
    //     });
    //
    //     if (tot != 3) {
    //         return false;
    //     }
    //
    //     var url = getRootPath() + '/common/doLogin';
    //     console.log(url);
    //     $("#loginform").attr("action", url);
    // });

});

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

//获取路径中的参数信息
function getParam(name){
    var curPath = window.document.location.href;
    console.log(curPath);
    var pos=curPath.indexOf("?");
    if(pos==-1){
        return null;
    }
    var paramPath=curPath.substring(pos+1,curPath.length);
    console.log(paramPath);
    pos=paramPath.lastIndexOf(name);
    if(pos==-1){
        console.log("没有"+name+"参数!");
        return null;
    }
    var tmp=paramPath.substring(pos,paramPath.length);

    var start=tmp.indexOf("=")+1;
    if(start==-1){
        //没有具体值
        return null;
    }
    var end=tmp.indexOf("?");
    if(end==-1){
        //最后一个参数，后面没有？
        end=tmp.length;
    }

    var value=tmp.substring(start,end);
    return value;
}

//获取参数方法
function GetUrlParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = encodeURI(window.location.search).substr(1).match(reg);
    if(r!=null)return unescape(r[2]); return null;
}