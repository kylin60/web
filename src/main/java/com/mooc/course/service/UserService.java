package com.mooc.course.service;

import com.mooc.course.bean.User;

public interface UserService {
    /**
     * 登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    User login(String username, String password);
}
