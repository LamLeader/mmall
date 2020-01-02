package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //todo MD5加密
        User user=userMapper.selectLogin(userName,password);
        if(user==null){
            return  ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);//清空密码
        return ServerResponse.createBySuccess("登录成功！",user);
    }
}
