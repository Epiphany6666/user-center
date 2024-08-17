package com.luoyan.usercenter.controller;
import com.luoyan.usercenter.model.User;
import com.luoyan.usercenter.model.domain.UserLoginRequest;
import com.luoyan.usercenter.model.domain.UserRegisterRequest;
import com.luoyan.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
* ⽤户接⼝
*
* @author yupi
*/
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
    if (userRegisterRequest == null) {
      return null;
    }
    String userAccount = userRegisterRequest.getUserAccount();
    String userPassword = userRegisterRequest.getUserPassword();
    String checkPassword = userRegisterRequest.getCheckPassword();
    if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
      return null;
    }
    return userService.userRegister(userAccount, userPassword, checkPassword);
  }

  @PostMapping("/login")
  public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
    if (userLoginRequest == null) {
      return null;
    }
    String userAccount = userLoginRequest.getUserAccount();
    String userPassword = userLoginRequest.getUserPassword();
    if (StringUtils.isAnyBlank(userAccount, userPassword)) {
      return null;
    }
    return userService.userLogin(userAccount, userPassword, request);
  }

}