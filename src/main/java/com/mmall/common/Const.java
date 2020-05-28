package com.mmall.common;

import java.nio.file.FileAlreadyExistsException;

/**
 * @description  常量类
 * @author 12484
 * @date 2020/1/4 21:25
 * @param
 * @return
 */
public class Const {

    public static  final  String CUREENT_USER="currentUser";
    public static  final String EMAIL="email";
    public static  final String USER_NAME="userName";
    public  interface  Role{
        int ROLE_CUSTOMER=0;//普通用户
        int ROLE_ADMIN=1;//管理员
    }

}
