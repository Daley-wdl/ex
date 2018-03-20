package com.exhibition.constants;

/**
 * 订单相关的常量
 */
public class OrderConstants {

    //订单状态,1=已完成，0=未完成,-1=已取消
    public static final String ORDER_COMPLETED = "1";
    public static final String ORDER_UNCOMPLETED = "0";
    public static final String ORDER_CANCELED = "-1";

    //配送方式 1=快递配送（免运费），2=快递配送（运费）,3=自取(默认方式)
    public static final String SHIPMENT_FREE = "1";
    public static final String SHIPMENT_CHARGE = "2";
    public static final String SHIPMENT_NONE = "3";

    //支付方式 0=线下支付，1=在线支付
    public static final String PAY_TYPE_ONLINE = "1";
    public static final String PAY_TYPE_OFFLINE = "0";

    //评论状态 0=未评论，1=已评论
    public static final String COMMENT_NONE = "0";
    public static final String COMMENT_ALREADY = "1";

    //是否已经付款
    public static final String PAYMENT_YES = "1";
    public static final String PAYMENT_NO = "0";

    //购物车中最多能添加多少商品
    public static final int SHOPPING_CART_MAX_NUM = 20;

    public static final int DEFAULT_PAGE_SIZE = 20;

    //默认邮编地址
    public static final String DEFAULT_ZIPCODE = "000000";

    public static final String ORDERITEM_IS_PAYOFF = "1";   //order_item单件已付款
    public static final String ORDERITEM_NOT_PAYOFF = "0";   //order_item单件未付款

    public static final int MAX_SELECT_ORDER_SIZE = 6;   //用户查询订单每页的最大请求数量

    public static final String IS_DELIVERED_YES = "1";   //已发货
    public static final String IS_DELIVERED_NO = "0";   //未发货

}
