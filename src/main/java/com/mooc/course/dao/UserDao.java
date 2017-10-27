package com.mooc.course.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.mooc.course.bean.User;

import java.util.List;

/**
 * 用户的数据访问操作
 */
@Repository
public interface UserDao {
    @Select("SELECT id, userName, nickName, userType,password " +
            "FROM person " +
            "WHERE userName = #{username} AND password = #{password}")
    List<User> login(@Param("username") String username, @Param("password") String password);
}