package com.luoyan.usercenter.service;

import com.luoyan.usercenter.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 52837
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-08-17 10:04:47
*/
public interface UserService extends IService<User> {
    /**
     * ⽤户注释
     * @param userAccount ⽤户账户
     * @param userPassword ⽤户密码
     * @param checkPassword 校验密码
     * @return 新⽤户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request      请求
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);
}
