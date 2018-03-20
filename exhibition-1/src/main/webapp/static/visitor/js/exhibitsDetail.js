
var id = decodeURI(GetUrlParam('id'));
var totalNum = 10000;
var loginHtml='    <div class="container" style="padding-left: 10px;padding-right: 10px;">\n' +
    '        <form class="form-horizontal" role="form">\n' +
    '            <div class="form-group">\n' +
    '                <label for="username" class="col-sm-2 control-label">用户名</label>\n' +
    '                <div class="col-sm-10">\n' +
    '                    <input type="text" class="form-control" id="username" placeholder="请输入用户名">\n' +
    '                </div>\n' +
    '            </div>\n' +
    '            <div class="form-group">\n' +
    '                <label for="password" class="col-sm-2 control-label">密码</label>\n' +
    '                <div class="col-sm-10">\n' +
    '                    <input type="password" class="form-control" id="password" placeholder="请输入密码">\n' +
    '                </div>\n' +
    '            </div>\n' +
    '            <div class="form-group">\n' +
    '                <div class="" align="center">\n' +
    '                    <a onclick="ajaxLogin();" class="btn btn-primary btn-lg" style="">登录</a>\n' +
    '                </div>\n' +
    '            </div>\n' +
    '        </form>\n' +
    '\n' +
    '        <div id="registerDiv" style="padding-top: 20px;">\n' +
    '            <a href="register.html"><span class="lead">免费注册</span></a>\n' +
    '        </div>\n' +
    '    </div>\n';
var exhibitorId = -1;

$(function () {
    init();
    setValue();
});

function goShop() {
    if (exhibitorId==-1) {
        return false;
    }
    window.location.href = "exhibitor.html?id=" + exhibitorId;
}

function addCart() {
    if (id == null) {
        window.location.href = 'index.html';
        // layer.open({
        //     content: '请先选择展品'
        //     ,time:2
        //     ,skin:'msg'
        // });
        // return false;
    }

    var re = /^[1-9]+$/; //判断字符串是否为数字 //判断正整数
    var numberHtml = $("#buyNumberSpan").html();
    if (!re.test(numberHtml)) {
        layer.open({
            content:'请输入正整数!'
            ,time:2
            ,skin:'msg'
        });
        return false;
    }
    var number = parseInt(numberHtml);
    layer.open({
        type: 2
        ,content: '添加中..'
    });
    $.ajax({
        url:getRootPath()+'/shoppingCart/addShoppingCart'
        ,type:'post'
        ,async:false
        ,data:{exhibitsId:id,buyNumber:number}
        ,error:function (req) {
            layer.closeAll();
            console.log(req.responseText);
            layer.open({
                content:'貌似出了点问题，但是问题不大，请重试'
                ,time:2
                ,skin:'msg'
            });
        }
        ,success:function (data) {
            console.log(data);
            layer.closeAll();
            if (data.status==1) {
                layer.open({
                    content:data.message
                    ,time:2
                    ,skin:'msg'
                });
            }
            if (data.status==0) {
                layer.open({
                    content:data.message
                    ,time:2
                    ,skin:'msg'
                });
            } if (data.status==-1) {
                //未登录
                layer.open({
                    content:loginHtml
                    ,time:0
                    ,title:'请先登录后，再添加'
                });
            }
        }
    });
}

function ajaxLogin() {
    var username = $("#username").val();
    var password = $("#password").val();
    $.ajax({
        url:getRootPath()+"/common/doLogin/user"
        ,data:{username:username,password:password}
        ,async:false
        ,type:'post'
        ,error:function (req) {
            console.log(req);
            layer.open({
                content:'貌似出了点小故障..'
                ,btn:["确认"]
                ,yes:function () {
                    layer.closeAll();
                }
            });
        }
        ,success:function (data) {
            if(data.status==-1) {
                layer.open({
                    content:loginHtml
                    ,time:0
                    ,title:data.message
                });
            }
            if(data.status==0) {
                layer.closeAll();
                layer.open({
                    content:'登录成功'
                    ,time:2
                    ,skin:'msg'
                });
            }
        }
    });
}

function addNum() {
    var numberHtml = $("#buyNumberSpan").html();
    var number = parseInt(numberHtml);
    if (number>totalNum) {
        layer.open({
            content:'抱歉，实在是给不了更多了...'
        });
        return false;
    }
    number++;
    $("#buyNumberSpan").html(number);
}
function subNum() {
    var numberHtml = $("#buyNumberSpan").html();
    var number = parseInt(numberHtml);
    if (number==1) {
        return false;
    }
    number--;
    $("#buyNumberSpan").html(number);
}

function init() {
    var footerHeight = $(".buttomItem").height() * 2 + 30 + 10;
    $("#moreComments").css("margin-bottom", footerHeight + 10 + 'px');
}

function setValue() {
    layer.open({
        type: 2
        , content: '疯狂加载中..'
    });
    $.ajax({
        url: getRootPath() + "/search/getExhibitInfo",
        data: {id: id},
        async: false,
        error: function (req) {
            console.log(req.responseText);
            layer.closeAll();
            layer.open({
                content: '加载失败...',
                time: 2,
                skin: "msg"
            });
        },
        success: function (data) {
            if (data.code == 0) {
                setValueToDom(data.data);
            }
            layer.closeAll();
        }
    });
}

function setValueToDom(value) {
    var exhibits = value["exhibits"];
    exhibitorId = exhibits["exhibitorId"];
    var comment = value["comment"];
    var photos = value["photos"];

    //轮播
    var carousel_indicators_html = "";
    var carousel_inner_html = '';
    if (!isEmpty(photos)) {
        $.each(photos, function (index, item) {
            carousel_indicators_html += createCarouselIndicatorsHtml(index);
            carousel_inner_html += createCarouselInnerHtml(index, item);
        });
    }
    $("#carousel-indicators").html(carousel_indicators_html);
    $("#carousel-inner").html(carousel_inner_html);

    //商品信息
    $("#exhibitsName").html(exhibits["exhibitsName"]);
    $("#intro").html(exhibits['intro']);
    $("#productGrade").html(exhibits["avgGrade"]);
    $("#price").html(exhibits["price"]);
    $("#remainingNumber").html(exhibits['number']);

    //评论
    if (comment!=null){
        $("#commentPhoto").html(comment['photoPath']);
        $("#commentText").html(comment['commentContent']);
        $("#commentDate").html(comment['commentDate']);
        $("#commentGrade").html(comment['productGrade']);
    } else {
        $("#commentText").html('该展品暂时还没有评价~~');
    }

}

//<li data-target="#div-carousel" data-slide-to="0" class="active"></li>
function createCarouselIndicatorsHtml(index) {
    //0" class="active"></li>
    var str = '<li data-target="#div-carousel" data-slide-to="' + index + '"';
    if (index == 0) {
        str += 'class="active"';
    }
    str += "></li>";
    return str;
}

//          <div class="item active">
//                <img src="../img/coffee.jpg" alt="...">
//                <div class="carousel-caption">
//                test
//                 </div>
//          </div>
function createCarouselInnerHtml(index, item) {
    var str = '<div class="item ';
    if (index == 0) {
        str += 'active';
    }
    str += '">';
    var imgPath = '<img src="' + item + '" >';
    console.log(imgPath);
    str += imgPath;
    str += '<div class="carousel-caption"></div></div>';
    return str;
}

function moreComments() {
    window.location.href = "comments.html?id=" + id;
}