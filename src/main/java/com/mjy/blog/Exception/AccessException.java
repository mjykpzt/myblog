package com.mjy.blog.Exception;

/**
 * @author mjy
 * @create 2020-06-29-20:52
 */
public class AccessException extends Exception{
    private Integer code;
    private String msg;
    private String errormsg;
    public AccessException(Integer code,String msg,String errormsg){
        super(errormsg);
        this.code =code;
        this.msg = msg;
    }

}
