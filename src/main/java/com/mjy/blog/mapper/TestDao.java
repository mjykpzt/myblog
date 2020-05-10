package com.mjy.blog.mapper;

import com.mjy.blog.Bean.Test;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author mjy
 * @create 2020-05-08-23:45
 */
public interface TestDao {
    @Select("SELECT id,name from menu WHERE id=1")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "id", property = "clist",
                    many = @Many(select = "com.mjy.blog.mapper.TestDao.t1")
            )
    })
    List<Test> t();

    @Select("SELECT id,name from menu WHERE parentId=#{id};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "id", property = "clist",
                    many = @Many(select = "com.mjy.blog.mapper.TestDao.t1")
            )
    })
    List<Test> t1(Integer id);


}
