
var goodFee = 0;
var shipmentFee = 0;
$(function () {
    init();
    reHeight();//调整高度

    var $radios = $(":checked");
//        console.log($radios);
//        $.each($radios,function (index, item) {
//            console.log($(item).attr('value'));
//        })
});

function backToIndex() {
    window.location.href = "index.html";
}

function confirmOrder() {
    layer.open({
        content:'确定生成订单吗?'
        ,btn:["确定","取消"]
        ,yes:function (index) {
            layer.close(index);
            layer.open({
                content:'生成订单中...'
                ,type:2
            });
            var ids = GetUrlParam("idStr");
            if (ids==null) {
                layer.open({
                    content:'未选择商品'
                    ,btn:["去购物"]
                    ,yes:function (index) {
                        layer.closeAll();
                        window.location.href = "index.html";
                    }
                });
                return false;
            }
            var tmpArray = ids.split(";");
            var idArray = new Array(tmpArray.length);
            for (var i=0;i<tmpArray.length;i++) {
                idArray[i] = parseInt(tmpArray[i]);
            }
            var radios = $(":checked");
            if (radios.length!=3) {
                layer.open({
                    content:'您好像漏了没什么没选..'
                    ,skin:'msg'
                    ,time:2
                });
                return false;
            }
            var addressRadio = radios.get(0);
            var addressId = $(addressRadio).attr('value');
            var payTppeRadio = radios.get(1);
            var payTypeId = $(payTppeRadio).attr('value');
            var shipRadio = radios.get(2);
            var shipId = $(shipRadio).attr('value');
            if (addressRadio==null) {
                layer.open({
                    content:'请选择收货地址'
                });
                return false;
            }
            console.log(idArray);
            console.log(addressId+"\t"+payTypeId+"\t"+shipId+"\t")

            $.ajax({
                url:getRootPath()+"/order/addOrder"
                ,type:'post'
                ,data:{orderAddressId:addressId,payType:payTypeId,shipmentType:shipId,shoppingCartIds:idArray}
//                    ,data:{shoppingCartIds:idArray}
                ,async:false
                ,error:function (req) {
                    console.log(req);
                    layer.open({
                        content:'生成订单失败，请重试'
                    });
                }
                ,success:function (data) {
                    layer.closeAll();
                    if (data.status==-1) {
                        layer.open({
                            content:data.message
                            ,btn:['登录']
                            ,yes:function (index) {
                                layer.closeAll();
                                window.location.href = 'login.html?redirect=confirmOrder';
                                return false;
                            }
                        });
                    }
                    if (data.status==1||data.code==1) {
                        layer.open({
                            content:data.message
                            ,skin:'msg'
                            ,time:3
                        });
                    }
                    if (data.status==0||data.code==0) {
                        if (payTypeId==0) {
                            //线下支付
                            layer.open({
                                content:data.message+"\n请到展商出完成支付"
                                ,btn:['确定']
                                ,shadeClose:false
                                ,yes:function (index) {
                                    layer.closeAll();
                                    window.location.href = "shoppingCart.html";
                                    return false;
                                }
                            });
                        } else {
                            //todo  支付
                            layer.open({content: '跳转到支付界面..'});
                        }
                    }
                }
            })
        }
    })
}

function cancle() {
    window.location.href = "shoppingCart.html";
}

function reHeight() {
    var height = $(".cartItem").height();
    var priceItemHeight = $(".itemPrice").height();
    $(".itemPrice").css("margin-top", (height-priceItemHeight)/2);

    var footerHeight = $(".buttomItem").height()*3+30+20+10;
    $("#cartList").css("margin-bottom",footerHeight+10+'px');
}

function init() {
    layer.open({
        type: 2
    });
    getAddress();
    var ids = GetUrlParam("idStr");
    if (ids==null||ids==''){
        layer.open({
            content:'您还未选择商品'
            ,btn:['我的购物车']
            ,shadeClose:false
            ,yes:function (index) {
                layer.closeAll();
                window.location.href = 'shoppingCart.html';
            }
        });
        return false;
    }
    var tmpArray = ids.split(";");
    var idArray = new Array(tmpArray.length);
    for (var i=0;i<tmpArray.length;i++) {
        idArray[i] = parseInt(tmpArray[i]);
    }
    getShipmentFee(idArray);
    getShoppingCartInfo(idArray);
    layer.closeAll();
}

function getShipmentFee(idArray) {
    $.ajax({
        url:getRootPath()+"/order/getBaseShipmentPrice"
        ,data:{shippingCartIds:idArray}
        , error:function (req) {
            layer.open({
                content: "获取邮费信息失败.."
            });
        },
        success:function (data) {
            if (data.data!=null) {
                shipmentFee = parseInt(data.data);
                $("#shipmentFee").html(shipmentFee);
//                    $("#totalPrice").html(goodFee + shipmentFee);
            }
        }
    })
}

function getShoppingCartInfo(ids) {
    $.ajax({
        url:getRootPath()+"/shoppingCart/getShoppingCartListById"
        ,data:{cartIds:ids}
        ,async:false
        ,error:function (req) {
            console.log(req);
            layer.open({content: '获取购买商品信息失败，请重试'});
        }
        ,success:function (data) {
            if (data.status==-1) {
                window.location.href = "login.html?redirect=confirmOrder";
                return false;
            }
            if (data.code==1) {
                layer.open({
                    content:data.message
                    ,skin:'msg'
                    ,time:2
                });
            }
            if (data.code==0) {
                $.each(data.data,function (index, item) {
                    setCartToDom(item);
                    goodFee += parseInt(item["productAmount"]);
                });
                $("#goodFee").html(goodFee);
                $("#totalPrice").html(goodFee + shipmentFee);
            }
        }
    })
}

function setCartToDom(cart) {
    var html='<div class="row cartItem">' +
        '        <div class="col-md-9 col-sm-9 col-xs-9" align="center">' +
        '            <div class="media">' +
        '                <div class="media-left media-middle">' +
        '                    <a href="#">' +
        '                        <img class="media-object exhibitsImg" src="'+cart['picImg']+'"/>' +
        '                    </a>' +
        '                </div>' +
        '                <div class="media-body" align="center">' +
        '                    <strong>' +
        '                        <p class="goodName media-heading">' +
        cart['exhibitsName']+
        '                        </p>' +
        '                    </strong>' +
        '                </div>' +
        '            </div>' +
        '        </div>' +
        '        <div class="col-md-3 col-sm-3 col-xs-3 itemPrice" align="center">' +
        '            <strong>￥<span>'+cart['productAmount']+'</span></strong>' +
        '        </div>' +
        '    </div>';
    $("#cartList").append(html);
}

function getAddress() {
    $.ajax({
        url:getRootPath()+"/orderAddress/getOrderAddressList"
        ,error:function (req) {
            console.log(req);
            layer.open({
                content:"获取地址失败"
                ,time:2
                ,skin:'msg'
            });
        }
        ,success:function (data) {
            if (data.status==-1) {
                window.location.href = "login.html?redirect=confirmOrder";
            }
            if (data.code==0) {
                $.each(data.data, function (index, item) {
                    setAddressToDom(item);
                });
            }
            if (data.code==1) {
                layer.open({
                    content:"获取地址失败"
                    ,time:2
                    ,skin:'msg'
                });
            }
        }
    })
}

function setAddressToDom(address) {
    var location=address['provinceName']+"省&nbsp;"+address['cityName']+"市&nbsp;"+address['districtName']+
        "区&nbsp;&nbsp;&nbsp;&nbsp;"+address['userAdress'];
    var html = '<div class="addressList row">' +
        '                        <div class="col-md-2 col-sm-2 col-xs-2">' +
        '<input value="'+address['addressId']+'" class="address" type="radio" name="addess" checked="checked">' +
        '                        </div>' +
        '                        <div class="col-md-10 col-sm-10 col-xs-10">' +
        '                            <span class="username">'+address['userName']+
        '</span><span class="telephone">'+address['userPhone']+'</span>' +
        '                            <p>'+location+'</p>' +
        '                        </div>' +
        '                    </div>';
    $("#addressContainer").append(html);
}

function addAddress() {
    layer.open({
        content:getAddAddressHtml()
//            ,title:'添加地址'
        ,anim: 'up'
        ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; ' +
        '-webkit-animation-duration: .5s; animation-duration: .5s;'
        ,type:1
        ,success:function (elem) {
            $("#addressForm").citySelect({
                url:'js/city.min.js'
                , prov:"湖北"
                , city:"武汉"
            });
            $("#closeBtn").click(function () {
                layer.closeAll();
            });
            $("#addAddressBtn").click(function () {
                var prov = $("#prov").find("option:selected").text();
                var city = $("#city").find("option:selected").text();
                var dist = $("#dist").find("option:selected").text();
                var userZipcode = $("#userZipcode").val();
                var userAdress = $("#userAdress").val();
                var userPhone = $("#userPhone").val();
                var userName = $("#userName").val();

                if (!filterStr(userName)){
                    layer.open({
                        content:"请勿在联系人姓名中加入特殊字符"
                    });
                    return false;
                }
                if (userAdress==null||userAdress.length==''||userAdress.length<3) {
                    layer.open({
                        content:"请输入正确的详细地址"
                    });
                    return false;
                }
                var codere= /^[0-9][0-9]{5}$/;
                if (!codere.test(userZipcode)){
                    layer.open({
                        content:"请输入正确的邮政编码"
                    });
                    return false;
                }


                var phoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
                if (!phoneReg.test(userPhone)){
                    layer.open({
                        content:"请输入正确的手机号码"
                    });
                    return false;
                }
                layer.open({
                    type:2
                });
                $.ajax({
                    url:getRootPath()+"/orderAddress/addOrderAddress"
                    ,async:false
                    ,type:'post'
                    ,data:{provinceName:prov,cityName:city,districtName:dist,userName:userName
                        ,userZipcode:userZipcode,userAdress:userAdress,userPhone:userPhone}
                    ,error:function (req) {
                        layer.closeAll();
                        console.log(req);
                        layer.open('注册失败，请重试');
                    }
                    ,success:function (data) {
                        layer.closeAll();
                        if (data.message!=null) {
                            layer.open({
                                content: data.message
                                ,time:3
                                ,skin:'msg'
                            });
                        }
                        if (data.status==0||data.code==0) {
                            if (data.data!=null) {
                                setAddressToDom(data.data);
                            }
                        }
                    }
                })
            });
        }
    });
}

