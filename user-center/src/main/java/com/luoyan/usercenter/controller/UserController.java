package com.luoyan.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luoyan.usercenter.common.BaseResponse;
import com.luoyan.usercenter.common.ResultUtils;
import com.luoyan.usercenter.model.domain.User;
import com.luoyan.usercenter.model.domain.UserLoginRequest;
import com.luoyan.usercenter.model.domain.UserRegisterRequest;
import com.luoyan.usercenter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.luoyan.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.luoyan.usercenter.constant.UserConstant.USER_LOGIN_STATE;

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
    String planetCode = userRegisterRequest.getPlanetCode();
    if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
      return null;
    }
    return userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
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

  @PostMapping("/logout")
  public Integer userLogout(HttpServletRequest request) {
    if (request == null) {
      return null;
    }
    return userService.userLogout(request);
  }

  @GetMapping("/current")
  public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
    Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
    User currentUser = (User) userObj;
    if (currentUser == null) {
      return null;
    }

    long userId = currentUser.getId();
    // todo 校验用户是否合法
    // 防止用户信息有变更，因此需要重新查询数据库
    User user = userService.getById(userId);
    User safetyUser = userService.getSafetyUser(user);
    return ResultUtils.success(safetyUser);
  }

  @GetMapping("/search")
  public List<User> searchUsers(String username, HttpServletRequest request) {
    // 仅管理员可查询
    if (isAdmin(request)) {
      return new ArrayList<>();
    }
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    if (StringUtils.isNotBlank(username)) {
      queryWrapper.like("username", username);
    }
    // 用户脱敏
    List<User> userList = userService.list(queryWrapper);
    return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
  }

  @PostMapping("/delete")
  public boolean deleteUser(@RequestBody long id, HttpServletRequest request) {
    if (isAdmin(request)) {
      return false;
    }
    if (id <= 0) {
      return false;
    }
    return userService.removeById(id);
  }

  private static boolean isAdmin(HttpServletRequest request) {
    // 仅管理员可查询
    Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
    User user = (User) userObj;
    return user == null || user.getUserRole() != ADMIN_ROLE;
  }

}