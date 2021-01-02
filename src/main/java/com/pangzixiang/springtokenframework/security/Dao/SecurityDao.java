package com.pangzixiang.springtokenframework.security.Dao;

import com.pangzixiang.springtokenframework.security.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SecurityDao {

    @Select("select * from USER where USERNAME = #{username}")
    User getUserByName(@Param("username") String username);

}
