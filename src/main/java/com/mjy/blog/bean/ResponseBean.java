package com.mjy.blog.bean;



/**
 * @author mjy
 * @create 2020-03-07-18:35
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
public class ResponseBean {
    private Integer status;
    private String msg;
    private Object data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseBean(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResponseBean(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static ResponseBean getSuccessResponse(String msg, Object data){
        return new ResponseBean(1,msg,data);
    }

    public static ResponseBean getSuccessResponse(String msg){
        return new ResponseBean(1,msg);
    }

    public static ResponseBean getFailResponse(String msg){
        return new ResponseBean(0,msg);
    }
}
