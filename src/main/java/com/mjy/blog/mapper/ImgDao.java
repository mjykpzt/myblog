package com.mjy.blog.mapper;

import com.mjy.blog.utils.imgJson;
import org.apache.ibatis.annotations.*;

/**
 *@author mjy
 *@date 2020/7/21
 */
public interface ImgDao {
    @Insert("insert into img_information (img_name,hash,size,bucket,upload_time) " +
            "values (#{key},#{hash},#{fsize},#{bucket},now())")
    int addImgInformation(imgJson imgJson);


    @Results(id="ImgInformation",value = {@Result(id = true,column = "id",property = "id"),
            @Result(column ="img_name",property = "key"),
            @Result(column = "hash",property = "hash"),
            @Result(column = "size",property = "fsize"),
            @Result(column = "upload_time",property ="upload_time" ),
            @Result(column = "bucket",property = "bucket")
    })
    @Select("select img_name,hash,size,bucket,upload_time from img_information where hash=#{hash}")
    imgJson findImgInformationByHash(String  hash);


    @ResultMap("ImgInformation")
    @Select("select img_name,hash,size,bucket,upload_time from img_information where id=#{id}")
    imgJson findImgInformationById(Integer id);

}
