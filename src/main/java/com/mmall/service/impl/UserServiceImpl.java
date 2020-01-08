package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public ServerResponse<User> login(String userName, String password) {
        int resultCount=userMapper.checkUsername(userName);
        if(resultCount==0){
            return  ServerResponse.createByErrorMessage("用户不存在");
         }
        //todo MD5加密 2020年1月7日22:27:37
        String md5Password=MD5Util.MD5EncodeUtf8(password);
        User user=userMapper.selectLogin(userName,password);
        if(user==null){
            return  ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);//清空密码
        return ServerResponse.createBySuccess("登录成功！",user);
    }

    @Override
    public ServerResponse<String> regitser(User user) {
        ServerResponse validateResponse=this.checkValidate(user.getUsername(),Const.USER_NAME);
        if(!validateResponse.isSuccess()){
            return  validateResponse;
        }
        validateResponse=this.checkValidate(user.getEmail(),Const.USER_NAME);
        if(!validateResponse.isSuccess()){
            return  validateResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
       int resultCount=userMapper.insert(user);
        if(resultCount==0){
            return  ServerResponse.createByErrorMessage("注册失败");
        }
        return  ServerResponse.createBySuccessMessage("注册成功");

    }

    @Override
    public ServerResponse<String> checkValidate(String str, String type) {

        if(StringUtils.isNotBlank(type)){
            //开始校验
            if (Const.USER_NAME.equals(type)){
                int resultCount=userMapper.checkUsername(str);
                if (resultCount>=1){
                    return  ServerResponse.createByErrorMessage("用户名已存在！");
                }
            }if (Const.EMAIL.equals(type)){
                int resultCount=userMapper.checkEmail(str);
                if (resultCount>=1){
                    return  ServerResponse.createByErrorMessage("邮箱已存在！");
                }
            }
            }else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse selectQuestion(String userName) {
        //开始校验
        ServerResponse validateResponse=this.checkValidate(userName,Const.USER_NAME);
        if(validateResponse.isSuccess()){
            ServerResponse.createByErrorMessage("用户不存在");
        }
        String questions=userMapper.selectQuestionByUsername(userName);
        if(StringUtils.isNotBlank(questions)){
            return ServerResponse.createBySuccess(questions);
        }
        return  ServerResponse.createBySuccessMessage("找回密码的问题是空的");
    }

    @Override
    public ServerResponse<String> checkAnswer(String userName, String question, String answer) {
        int resultCount=userMapper.checkAnswer(userName,question,answer);
        if(resultCount>0){
            //说明问题以及问题答案是这个用户的
            String forgotToken= UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+userName,forgotToken);
            return  ServerResponse.createBySuccess(forgotToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String userName, String newPassword, String forgetToken) {

        if (StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数值错误，token值需要传递");
        }
        ServerResponse validateResponse=this.checkValidate(userName,Const.USER_NAME);
        if(validateResponse.isSuccess()){
            ServerResponse.createByErrorMessage("用户不存在");
        }
        String token=TokenCache.getKey(TokenCache.TOKEN_PREFIX+userName);
        if (StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }
        if(StringUtils.equals(forgetToken,token)){
            String md5Password=MD5Util.MD5EncodeUtf8(newPassword);
            int rowCount=userMapper.updatePasswordByUsername(userName,md5Password);
            if(rowCount>0){
                return  ServerResponse.createBySuccessMessage("密码重置成功");
            }
        }else{
            return  ServerResponse.createByErrorMessage("token错误请重新登录获取token值");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<String> resetPassword(String oldPassword,String newPassword,User user) {
        //防止横向越权要校验下用户的旧密码一定是指定这个用户
        int  rowCount=userMapper.checkPassword(MD5Util.MD5EncodeUtf8(oldPassword),user.getId());
        if(rowCount==0){
            return  ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(newPassword));
         rowCount=userMapper.updateByPrimaryKeySelective(user);
         if (rowCount>0){
             return  ServerResponse.createBySuccessMessage("密码更新成功");
         }
        return  ServerResponse.createBySuccessMessage("密码更新失败");
    }

    @Override
    public ServerResponse<User> updateUserInfo(User user) {
        //userName 不能更新
        //email也要进行一个校验，校验新的email是不是已经存在，并且存在的email如果相同的话，不能是我们当前的这个用户的。
        int resultCount=userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount>0){
            return  ServerResponse.createByErrorMessage("email已存在，请更换email再尝试更新");
        }
        User updateUser=new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int updateCount=userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount>0){
            return  ServerResponse.createBySuccess("个人信息更新成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");

    }

    @Override
    public ServerResponse<User> getUserInfomation(Integer userId) {
        User user=userMapper.selectByPrimaryKey(userId);
        if (user==null){
            return ServerResponse.createBySuccessMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse checkRoleAdmin(User user) {
        if (user!=null&&user.getRole().intValue()==Const.Role.ROLE_ADMIN){
            return  ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }


}
