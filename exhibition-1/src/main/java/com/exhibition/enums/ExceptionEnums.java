package com.exhibition.enums;

/**
 * Created by final on 17-7-6.
 */
public enum ExceptionEnums {
    Error(-1,"失败"),
    UsernameExists(1,"用户名已存在"),
    LackInfo(2,"信息不完整"),
    WrongId(3,"id为空或者不合法"),
    UserNotExist(4, "该用户不存在"),
    WrongLength(5,"数据长度不符合要求"),
    LackId(6,"缺少用户Id"),
    ExhibitsNameExists(7,"展品名称已存在"),
    WrongStatus(8,"status错误"),
    SurpassMaxCount(9,"数量已经超过限制"),
    StringNotValid(10,"输入包含不合法内容"),
    Overflow(11, "产生溢出"),
    SaleOut(12,"库存不足"),
    ExhibitsNotExists(13, "展品不存在"),
    EmptyShoppingCart(14,"所选购物车为空"),
    ShoppingCartExist(15,"该展品已经添加过了"),
    OrderAddressIsInUse(16,"仍有未完成订单使用该地址")
    ;

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ExceptionEnums(int code, String message) {

        this.code = code;
        this.message = message;
    }
}
