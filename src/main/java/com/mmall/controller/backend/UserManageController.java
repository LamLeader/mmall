package com.mmall.controller.portal.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: 12484
 * @date: Created in 2020/1/5 0:05
 * @version: 1.0
 * @modified By:
 */
@Controller
@RequestMapping(value = "/manage/user/")
public class UserManageController {

    @Autowired
    private IUserService iUserService;
    /**
     * @description  管理员登录
     * @author 12484
     * @date 2020/1/5 0:12
     * @param userName
     * @param password
     * @param session
     * @return com.mmall.common.ServerResponse<com.mmall.pojo.User>
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String userName, String password, HttpSession session){
       ServerResponse<User> response=iUserService.login(userName,password);
       if(response.isSuccess()){
           User user=response.getData();
           if(user.getRole()== Const.Role.ROLE_ADMIN){
               //判断是否是管理员登录
               session.setAttribute(Const.CUREENT_USER,user);
               return  response;
           }else{
               return  ServerResponse.createByErrorMessage("不是管理员无法登录");
           }
       }
       return  response;

    }






}
