package com.exhibition.vo;


/**
 *
 * @Author 张元友
 * @ClassName ReplyResult.java
 * @Description 封装状态信息--status和message
 * @Time 2017年4月11日 下午6:54:55
 */
public class  ReplyResult {

    private Integer status;
    private String message;

    public ReplyResult(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 测试代码，运行结果为: {"status":1,"message":"请上传图片"}
     */
//    public static void main(String[] args) {
//        Gson gson = new Gson();
//        String json = gson.toJson(new ReplyResult(1,"请上传图片"));
//        System.out.println(json);
//    }

}