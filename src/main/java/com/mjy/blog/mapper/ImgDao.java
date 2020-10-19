package com.mjy.blog.mapper;

import com.mjy.blog.utils.imgJson;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *@author mjy
 *@date 2020/7/21
 */
public interface ImgDao {
    @Insert("insert into img_information (img_name,hash,size,bucket,upload_time,user_id) " +
            "values (#{key},#{hash},#{fsize},#{bucket},now(),#{user_id})")
    int addImgInformation(imgJson imgJson);


    /**
     *
     *通过文件hash值查找imgJson
     *
     * @param hash 文件hash值
     * @return: java.util.List<com.mjy.blog.utils.imgJson>
     * @author: 0205
     */
    @Results(id="ImgInformation",value = {@Result(id = true,column = "id",property = "id"),
            @Result(column ="img_name",property = "key"),
            @Result(column = "hash",property = "hash"),
            @Result(column = "size",property = "fsize"),
            @Result(column = "upload_time",property ="upload_time" ),
            @Result(column = "bucket",property = "bucket"),
            @Result(column = "user_id",property = "user_id")
    })
    @Select("select img_name,hash,size,bucket,upload_time from img_information where hash=#{hash}")
    List<imgJson> findImgInformationByHash(String hash);


    @ResultMap("ImgInformation")
    @Select("select img_name,hash,size,bucket,upload_time from img_information where id=#{id}")
    imgJson findImgInformationById(Integer id);

    @Select("SELECT EXISTS(select 1 from img_information where hash=#{hash})")
    int isHasImg(String hash);

}
