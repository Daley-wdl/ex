var page = 1;
var size = 2;
$(function () {
    page = 1;
    init();
});

function init() {
    layer.open({
        type: 2
        , content: '加载中'
    });
    getAddress();
    getOrderInfo();
}

function moreOrder() {
    getOrderInfo();
}

function deleteAddress() {
    if ($('input:radio[name="address"]:checked') == null) {
        layer.open({content: '请选择需要删除的地址'});
    }
    var id = $('input:radio[name="address"]:checked').attr('value');
    $.ajax({
        url: getRootPath() + '/orderAddress/deleteOrderAddress'
        , data: {orderAddressId: id}
//            ,async:false
        , error: function (req) {
            console.log(req);
            layer.open({
                content: '删除失败,请重试'
                , skin: 'msg'
                , time: 2
            });
        }
        , success: function (data) {
            if (data.status == 0) {
                $('input:radio[name="address"]:checked').parent().parent().remove();
            }
            if (data.status == 0) {
                layer.open({
                    content: data.message
                    , skin: 'msg'
                    , time: 2
                })
            }
        }
    });
}

function getAddress() {
    $.ajax({
        url: getRootPath() + "/orderAddress/getOrderAddressList"
        , error: function (req) {
            console.log(req);
            layer.open({
                content: "获取地址失败"
                , time: 2
                , skin: 'msg'
            });
        }
        , success: function (data) {
            if (data.status == -1) {
                window.location.href = "login.html?redirect=user";
            }
            if (data.code == 0) {
                $.each(data.data, function (index, item) {
                    setAddressToDom(item);
                });
            }
            if (data.code == 1) {
                layer.open({
                    content: "获取地址失败"
                    , time: 2
                    , skin: 'msg'
                });
            }
        }
    })
}

function setAddressToDom(address) {
    var location = address['provinceName'] + "省&nbsp;" + address['cityName'] + "市&nbsp;" + address['districtName'] +
        "区&nbsp;&nbsp;&nbsp;&nbsp;" + address['userAdress'];
    var html = '<div class="addressList row">' +
        '                        <div class="col-md-2 col-sm-2 col-xs-2">' +
        '<input value="' + address['addressId'] + '" class="address" type="radio" name="address" checked="checked">' +
        '                        </div>' +
        '                        <div class="col-md-10 col-sm-10 col-xs-10">' +
        '                            <span class="username">' + address['userName'] +
        '</span><span class="telephone">' + address['userPhone'] + '</span>' +
        '                            <p>' + location + '</p>' +
        '                        </div>' +
        '                    </div>';
    $("#addressContainer").append(html);
}

function getOrderInfo() {
    layer.open({
        type: 2
        , content: '加载中'
    });
    $.ajax({
        url: getRootPath() + "/order/getOrdersForUser"
        , async: false
        , data: {page: page, size: size}
        , error: function (req) {
            console.log(req);
            layer.closeAll();
            layer.open({content: '获取订单信息失败，请重试'});
        }
        , success: function (data) {
            if (data.status == -1) {
                window.location.href = "login.html?redirect=user";
            }
            if (data.code == 1) {
                layer.open({content: '获取订单信息失败，请重试'});
            }
            if (data.code == 0) {
                var totalData = data.data;
                if (totalData.length == 0) {
                    layer.open({
                        content: '已经到了最后一页'
                        , time: 1
                        , skin: 'msg'
                        , end: function () {
                            layer.closeAll();
                        }
                    });
                    return false;
                }
                page++;
                totalData.reverse();
                $.each(totalData, function (index, item) {
                    var order = item.order;
                    var items = item.orderItems;
                    var canceledItems = item.canceledItems;
                    var itemArray = new Array();
                    var index = 0;
                    for (var i = 0; i < items.length; i++) {
                        if (items[i].orderId == order.orderId) {
                            itemArray[index] = items[i];
                            index++;
                        }
                    }
                    if (itemArray.length != 0) {
                        itemArray.reverse();
                        setOrderInfoToDom(order, itemArray);
                    }

                    index = 0;
                    var canceledArray = new Array();
                    for (var i = 0; i < canceledItems.length; i++) {
                        if (canceledItems[i].orderId == order.orderId) {
                            canceledArray[index] = canceledItems[i];
                            index++;
                        }
                    }
                    if (canceledArray.length != 0) {
                        canceledArray.reverse();
                        setCanceledOrderInfoToDom(order, canceledArray);
                    }
                });
                layer.closeAll();
            }
        }
    });

}

function setOrderInfoToDom(order, items) {
    var orderId = parseInt(order.orderId);
    var time = order.createTime;
    var orderStatus = order.orderStatus;
    var orderStatus = order.orderStatus;
    var orderStatusStr = '';
    if (orderStatus == '1')
        orderStatusStr = "已完成";
    else if (orderStatus == '0')
        orderStatusStr = '未完成';
    else
        orderStatusStr = '已取消';

    var html = '<div class="row order">' +
        '                        <div class="orderTitle">' +
        '                            <span class="text-muted pull-right" style="padding-right: 20px;">' + time +
        '<span onclick="cancelOrder(' + orderId + ');" class="glyphicon glyphicon-trash" style="padding-left: 10px;"></span></span>' +
        '                            <span>' +
        '                                订单号:<span>' + orderId + '</span>' +
        '                                <small><span style="padding-left: 10px;" class="text-muted">' + orderStatusStr + '</span></small>' +
        '                            </span>' +
        '                        </div>';
    $.each(items, function (index, item) {
        var shipId = item['shipmentId'];
        var isPayOff = item["isPayOff"];
        var isPayOffStr = '';
        var commentBtnStr = '';
        if (isPayOff == '1') {
            isPayOffStr = "已支付";
            if (item.commentStatus=='0'){
                commentBtnStr = '<span><a onclick="comment(' + item.exhibitsId+','+item.orderItemId + ',this);" href="javascript:void(0);" class="btn btn-default">评价</a></span>';
            } else {
                commentBtnStr = '<a onclick="replyDetail(' + item.orderItemId + ');" href="javascript:void(0);" class="btn btn-default">查看回复</a>';
            }
        }
        else {
            isPayOffStr = "未支付";
//                commentBtnStr = '<a onclick="cancelOrder('+orderId+')" href="javascript:void(0);" class="btn btn-default">取消订单</a>';
        }
        var bottmChooseStr = '';
        if (shipId == null) {
            shipId = '暂无';
            bottmChooseStr = '<div>' +
                '                                    <span ' +
                '                                          style="color: #f4191c;margin-right: 20px;">￥' + item['productAmount'] + '</span>' +
                '                                    <span class="text-muted" " class="btn btn-default">' + isPayOffStr + '</span>' +
                '                                </div>';
        } else {
            bottmChooseStr = '<div><span class="text-muted" style="margin-right: 20px;">' +
                isPayOffStr +
                '                                    </span>' +
                commentBtnStr +
                '                                    </div>';
        }

        html += '                        <div class="orderItemList">' +
            '                            <div class="media">' +
            '                                <div class="media-left media-middle">' +
            '                                    <a href="#">' +
            '                                        <img style="max-width: 100px;" class="media-object" src="' + item['picImg'] + '">' +
            '                                    </a>' +
            '                                </div>' +
            '                                <div class="media-body">' +
            '                                    <p class="media-heading">' + item['exhibitsName'] + '</p>' +
            '                                    <div style="padding-top: 10px;">' +
            '                                        <span style="color: #f4191c">￥<span style="">' + item['price'] + '</span></span>' +
            '                                        <span style="padding-left: 40px;">' +
            '                                        x<span style="padding-right: 10px;padding-left: 5px;">' + item['buyNumber'] + '</span>' +
            '                                    </span>' +
            '                                    </div>' +
            '                                    <div>' +
            '                                        <p class="text-muted">' +
            '                                            快递单号:<span>' + shipId + '</span>' +
            '                                        </p>' +
            '                                    </div>' +
            bottmChooseStr +
            '                                </div>' +
            '                            </div>';

    });
    html += '                        </div>' +
        '                    </div>';

    $("#orderContainer").append(html);
}

function setCanceledOrderInfoToDom(order, items) {
    var orderId = order.orderId;
    var time = order.createTime;
    var orderStatus = order.orderStatus;
    var orderStatus = order.orderStatus;
    var orderStatusStr = '';
    if (orderStatus == '1')
        orderStatusStr = "已完成";
    else if (orderStatus == '0')
        orderStatusStr = '未完成';
    else
        orderStatusStr = '已取消';

    var html = '<div class="row order">' +
        '                        <div class="orderTitle">' +
        '                            <span class="text-muted pull-right" style="padding-right: 20px;">' + time + '</span>' +
        '                            <span>' +
        '                                订单号:<span>' + orderId + '</span>' +
        '                                <small><span style="padding-left: 10px;" class="text-muted">' + orderStatusStr + '</span></small>' +
        '                            </span>' +
        '                        </div>';
    $.each(items, function (index, item) {
        var shipId = item['shipmentId'];
        var isPayOff = item["isPayOff"];
        var isPayOffStr = '';
        if (isPayOff == '1')
            isPayOffStr = "已支付";
        else
            isPayOffStr = "未支付";
        var bottmChooseStr = '';
        if (shipId == null) {
            shipId = '暂无';
            bottmChooseStr = '<div>' +
                '                                    <span ' +
                '                                          style="color: #f4191c;margin-right: 20px;">￥' + item['productAmount'] + '</span>' +
                '                                    <a href="javascript:void(0);" class="btn btn-default">查看回复</a>' +
                '                                </div>';
        } else {
            bottmChooseStr = '<div><span class="text-muted" style="margin-right: 20px;">' +
                isPayOffStr +
                '                                    </span>' +
                '                                        <a onclick="comment();" href="javascript:void(0);" class="btn btn-default">评价</a>' +
                '                                    </div>';
        }

        html += '                        <div class="orderItemList">' +
            '                            <div class="media">' +
            '                                <div class="media-left media-middle">' +
            '                                    <a href="#">' +
            '                                        <img style="max-width: 100px;" class="media-object" src="' + item['picImg'] + '">' +
            '                                    </a>' +
            '                                </div>' +
            '                                <div class="media-body">' +
            '                                    <p class="media-heading">' + item['exhibitsName'] + '</p>' +
            '                                    <div style="padding-top: 10px;">' +
            '                                        <span style="color: #f4191c">￥<span style="">' + item['price'] + '</span></span>' +
            '                                        <span style="padding-left: 40px;">' +
            '                                        x<span style="padding-right: 10px;padding-left: 5px;">' + item['buyNumber'] + '</span>' +
            '                                    </span>' +
            '                                    </div>' +
            '                                    <div>' +
            '                                        <p class="text-muted">' +
            '                                            快递单号:<span>' + shipId + '</span>' +
            '                                        </p>' +
            '                                    </div>' +
            //                bottmChooseStr+
            '                                </div>' +
            '                            </div>';

    });
    html += '                        </div>' +
        '                    </div>';

    $("#orderContainer").append(html);
}

/*
显示评价面板
 */
function comment(id,orderItemId,obj) {
    var html = '<div class="container">' +
        '        <form class="form-horizontal" role="form">' +
        '            <div class="form-group">' +
        '                <label for="commentContext" class="col-sm-2 control-label">评价内容</label>' +
        '                <div class="col-sm-10">' +
        '                    <textarea class="form-control" maxlength="200" rows="3" name="commentContext" id="commentContext" placeholder="请输入评价内容"></textarea>' +
        //            '                    <input type="text" class="form-control" name="commentContext" id="commentContext" placeholder="请输入评价内容">' +
        '                </div>' +
        '            </div>' +
        '            <div class="form-group">' +
        '                <label for="product_grade" class="col-sm-2 control-label">评价</label>' +
        '                <div class="col-sm-10">' +
        '                    <input id="product_grade" name="product_grade"' +
        '                           type="number" class="rating" min=0 max=5 step=1 data-size="xs">' +
        '                </div>' +
        '            </div>' +
        '            <div class="openPageBtns" align="center">' +
        '                <a href="javascript:void(0);" onclick="layer.closeAll();" class="btn btn-primary btn-lg">退出</a>' +
        '                <a href="javascript:void(0);" onclick="sendComment(' + id+','+orderItemId + ');" class="btn btn-default btn-lg">发送</a>' +
        '            </div>' +
        '        </form>' +
        '    </div>';

    layer.open({
        content: html
        , anim: 'up'
        , style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; ' +
        '-webkit-animation-duration: .5s; animation-duration: .5s;'
        , type: 1
        , success: function () {
            $("#product_grade").rating({
                showClear: false
            });
        }
    });
}

/*
发送评价信息
 */
function sendComment(id,orderItemId) {
    var value = $("#commentContext").val();
    var grade = $("#product_grade").val();
    var gradeReg = /^[12345]$/;
    if (!gradeReg.test(grade)) {
        layer.open({
            content: '请先为商品打分'
            , skin: 'msg'
            , time: 2
        });
        return false;
    }
    value = filter(value);
    console.log(value);
    $.ajax({
        url: getRootPath() + "/comment/addComment"
        , async: false
        , data: {productId: id, commentContent: value, productGrade: grade,orderItemId:orderItemId}
        , type: 'post'
        , error: function (req) {
            console.log(req);
        }
        , success: function (data) {
            layer.closeAll();
            if (data.status == -1) {
                window.location.href = "login.html?redirect=user";
                return false;
            }
            if (data.status==1) {
                layer.open({
                    content: data.message
                    , skin: 'msg'
                    , time: 2
                });
            }
            if (data.status==0) {
                location.reload();
            }
        }
    });

}

function replyDetail(orderItemId) {
    window.location.href = "reply.html?orderItemId=" + orderItemId;
}

function cancelOrder(orderId) {
    layer.open({
        content: '确定要取消订单吗?'
        , btn: ['删除', '取消']
        , skin: 'footer'
        , yes: function (index) {
            layer.open({
                type: 2
                , content: "取消中.."
            });
            $.ajax({
                url: getRootPath() + '/order/cancelOrder'
                , async: false
                , data: {orderId: orderId}
                , error: function (req) {
                    console.log(req);
                    layer.closeAll();
                    layer.open({content: '取消订单失败，请检查网络连接'});
                }
                , success: function (data) {
                    layer.closeAll();
                    if (data.status == -1) {
                        window.location.href = "login.html?redirect=user";
                        return false;
                    }
                    if (data.status == 0) {
                        layer.open({
                            content: data.message
                            , skin: 'msg'
                            , time: 2
                        });
                        $(this).parent().parent().parent().remove();
                        return false;
                    }
                    if (data.status == 1) {
                        layer.open({content: data.message});
                    }
                }
            });
        }
    });
}

function backToIndex() {
    window.location.href = "index.html";
}
