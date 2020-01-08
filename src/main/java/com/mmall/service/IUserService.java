package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

public interface IUserService {

   public ServerResponse<User> login(String userName, String password);

   public ServerResponse<String> regitser(User user);

   public ServerResponse<String> checkValidate(String str,String type);

   public ServerResponse selectQuestion(String userName);

   public  ServerResponse<String> checkAnswer(String userName,String question,String answer);

   public ServerResponse<String> forgetResetPassword(String userName,String newPassword,String forgetToken);

   public ServerResponse<String> resetPassword(String oldPassword,String newPassword,User user);

   public ServerResponse<User> updateUserInfo(User user);

   public ServerResponse<User> getUserInfomation(Integer userId);

   /**
    * @description  校验是否是管理员
    * @author 12484
    * @date 2020/1/6 22:12
    * @param user
    * @return com.mmall.common.ServerResponse
    */
   public ServerResponse checkRoleAdmin(User user);
}
