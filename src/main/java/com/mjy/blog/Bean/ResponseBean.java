package com.mjy.blog.Bean;

import lombok.Data;

/**
 * @author mjy
 * @create 2020-03-07-18:35
 */
@Data
public class ResponseBean {
    private Integer status;
    private String msg;
    private Object data;

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
