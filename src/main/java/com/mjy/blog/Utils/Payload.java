package com.mjy.blog.Utils;

import lombok.Data;

import java.util.Date;

/**
 * @author mjy
 * @create 2020-03-05-16:29
 */
@Data
public class Payload<T> {
    private String id;
    private T userInfo;
    private Date expiration;
}
