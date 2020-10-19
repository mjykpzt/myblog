package com.mjy.blog.utils;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *@author mjy
 *@date 2020/7/21
 */
public class GetJsonImg {
    private final static Pattern COMPILE = Pattern.compile("\"callback_body\":\"?(\\{.*?})");
    private final static ObjectMapper objectmapper = new ObjectMapper();

    /**
     *获取七牛云回调字符串里面的callback_body部分
     *
     * @param jsonString
     * @return: java.lang.String
     * @author: 0205
     */
    public static String getJsonBodyString(String jsonString){
        Matcher matcher = COMPILE.matcher(jsonString);
        if (matcher.groupCount()>1){
            return null;
        }
        if (matcher.find()){
            return matcher.group(1);
        }
        return null;
    }


    /**
     * @param jsonString
     * @param tClass
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T getJsonBodyObject(String jsonString, Class<T> tClass) throws IOException {
//        String jsonBodyString = getJsonBodyString(jsonString);
        if (jsonString!=null){
            return objectmapper.readValue(jsonString, tClass);
        }
        return null;
    }


}
