package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
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
 * @description  用户类
 * @author 12484
 * @date 2020/1/4 21:23
 * @param
 * @return
 */
@Controller
@RequestMapping(value="/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;
    /**
     * @description  用户登录 host:http://localhost:8088  path:/mmall_war/user/login.do
     * SELECT id, username, PASSWORD, email, phone, question, answer, role,
     * create_time, update_time FROM mmall_user WHERE username ='admin' AND PASSWORD ='427338237BD929443EC5D48E24FD2B1A'
     * 427338237BD929443EC5D48E24FD2B1A 37ED98BEE9AC2E88A73EB6F1474CDBBA
     * @author 12484
     * @date 2020/1/4 21:23
     * @param userName
     * @param password
     * @param session
     * @return com.mmall.common.ServerResponse<com.mmall.pojo.User>
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String userName, String password, HttpSession session){
        ServerResponse<User> response= iUserService.login(userName,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CUREENT_USER,response.getData());
        }
        return response;
    }

    /**
     * @description  用户登出
     * @author 12484
     * @date 2020/1/4 21:22
     * @param session
     * @return com.mmall.common.ServerResponse<java.lang.String>
     */
    @RequestMapping(value = "loginOut.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> loginOut(HttpSession session){
          session.removeAttribute(Const.CUREENT_USER);
          return ServerResponse.createBySuccess();
    }

    /**
     * @description  用户注册
     * @author 12484
     * @date 2020/1/4 21:22
     * @param user
     * @return com.mmall.common.ServerResponse<java.lang.String>
     */
    @RequestMapping(value = "regitser.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> regitser(User user){

        return iUserService.regitser(user);
    }

    /**
     * @description  检测邮箱和用户名是否在数据库中存在
     * @author 12484
     * @date 2020/1/4 21:42
     * @param str
     * @param type
     * @return com.mmall.common.ServerResponse<java.lang.String>
     */
    @RequestMapping(value = "checkValidate.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValidate(String str,String type){

        return  iUserService.checkValidate(str,type);
    }
    /**
     * @description  获取当前用户信息
     * @author 12484
     * @date 2020/1/4 21:45
     * @param session
     * @return com.mmall.common.ServerResponse<com.mmall.pojo.User>
     */
    @RequestMapping(value = "getUserInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
       User user=(User) session.getAttribute(Const.CUREENT_USER);
       if(null!=user){
           return  ServerResponse.createBySuccess(user);
       }
       return  ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息");
    }
    /**
     * @description  根据用户名获取问题
     * @author 12484
     * @date 2020/1/4 22:08
     * @param userName
     * @return com.mmall.common.ServerResponse
     */
    @RequestMapping(value = "forgetGetQuestion.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse forgetGetQuestion(String userName){

       return iUserService.selectQuestion(userName);
    }
    /**
     * @description  根据用户名 和问题获取答案
     * @author 12484
     * @date 2020/1/4 22:12
     * @param userName
     * @param question
     * @param answer
     * @return com.mmall.common.ServerResponse<java.lang.String>
     */
    @RequestMapping(value = "forgetCheckAnwser.do",method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse<String> forgetCheckAnwser(String userName,String question,String answer){
       return  iUserService.checkAnswer(userName,question,answer);
   }
    /**
     * @description 忘记密码 重置密码
     * @author 12484
     * @date 2020/1/4 23:10
     * @param userName
     * @param newPassword
     * @param forgetToken
     * @return com.mmall.common.ServerResponse<java.lang.String>
     */
    @RequestMapping(value = "forgetResetPassword.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String userName,String newPassword,String forgetToken){
        return  iUserService.forgetResetPassword(userName,newPassword,forgetToken);
   }
    /**
     * @description  登录状态下的重置密码
     * @author 12484
     * @date 2020/1/4 23:16
     * @param session
     * @param newPassword
     * @param oldPassword
     * @return com.mmall.common.ServerResponse<java.lang.String>
     */
    @RequestMapping(value = "resetPassword.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,String oldPassword,String newPassword){
        User user=(User) session.getAttribute(Const.CUREENT_USER);
        if(null==user){
            return  ServerResponse.createByErrorMessage("用户未登陆");
        }
        return iUserService.resetPassword(oldPassword,newPassword,user);
   }
    /**
     * @description  更改个人信息
     * @author 12484
     * @date 2020/1/4 23:51
     * @param session
     * @param user
     * @return com.mmall.common.ServerResponse<com.mmall.pojo.User>
     */
    @RequestMapping(value = "updateUserInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateUserInfo(HttpSession session, User user){
       User currentUser=(User) session.getAttribute(Const.CUREENT_USER);
       if(null==currentUser){
           return  ServerResponse.createByErrorMessage("用户未登陆");
       }
       user.setId(currentUser.getId());
       user.setUsername(currentUser.getUsername());
       ServerResponse<User> response=iUserService.updateUserInfo(user);
       if (response.isSuccess()){
           response.getData().setUsername(currentUser.getUsername());
           session.setAttribute(Const.CUREENT_USER,response.getData());
       }
       return  response;
   }
    /**
     * @description  获取用户详细信息
     * @author 12484
     * @date 2020/1/5 0:02
     * @param session
     * @return com.mmall.common.ServerResponse<com.mmall.pojo.User>
     */
    @RequestMapping(value = "getUserInformation.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInformation(HttpSession session){
       User currentUser=(User) session.getAttribute(Const.CUREENT_USER);
       if(null==currentUser){
           return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，需要强制登录,status=10");
       }
       return  iUserService.getUserInfomation(currentUser.getId());
   }
















}
