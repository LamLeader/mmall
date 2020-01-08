package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: 12484
 * @date: Created in 2020/1/6 22:23
 * @version: 1
 * @modified By:
 */
@Controller
@RequestMapping(value = "/manage/category/")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCatecoryService;

    /**
     * @description  添加商品品类
     * @author 12484
     * @date 2020/1/6 23:01
     * @param session
     * @param categoryName
     * @param parentId
     * @return com.mmall.common.ServerResponse
     */
    @RequestMapping(value = "addCategory.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,String categoryName,
                                      @RequestParam(value = "parentId",defaultValue ="0") Integer parentId){
        User user=(User) session.getAttribute(Const.CUREENT_USER);
        if (null==user){
             return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查是否是管理员
        if(iUserService.checkRoleAdmin(user).isSuccess()){
            //是增加分类的逻辑处理
            return iCatecoryService.addCategory(categoryName,parentId);
        }else{
            return  ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }
    /**
     * @description    管理员修改商品品类名称
     * @author 12484
     * @date 2020/1/6 23:15
     * @param session
     * @param categoryName
     * @param categoryId
     * @return com.mmall.common.ServerResponse
     */
    @RequestMapping(value = "modifyCategoryName.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse modifyCategoryName(HttpSession session,String categoryName,
                                      @RequestParam(value = "categoryId") Integer categoryId){
        User user=(User) session.getAttribute(Const.CUREENT_USER);
        if (null==user){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查是否是管理员
        if(iUserService.checkRoleAdmin(user).isSuccess()){
            //是增加分类的逻辑处理
            return iCatecoryService.updateCategoryName(categoryId,categoryName);
        }else{
            return  ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }
    /**
     * @description   管理员查询子节点分类
     * @author 12484
     * @date 2020/1/6 23:43
     * @param session
     * @param categoryId
     * @return com.mmall.common.ServerResponse
     */
    @RequestMapping(value = "getChildParallelCategory.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getChildParallelCategory(HttpSession session, @RequestParam(value = "categoryId",defaultValue ="0") Integer categoryId){
        User user=(User) session.getAttribute(Const.CUREENT_USER);
        if (null==user){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查是否是管理员
        if(iUserService.checkRoleAdmin(user).isSuccess()){
            //查询的子节点的category 信息并且不递归，保存平级。
            return  iCatecoryService.getChildParelleCategory(categoryId);
        }else{
            return  ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * @description   管理员查询子节点分类(递归查询)
     * @author 12484
     * @date 2020/1/6 23:43
     * @param session
     * @param categoryId
     * @return com.mmall.common.ServerResponse
     */
    @RequestMapping(value = "getCategoryDeepChildParallelCategory.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getCategoryDeepChildParallelCategory(HttpSession session, @RequestParam(value = "categoryId",defaultValue ="0") Integer categoryId){
        User user=(User) session.getAttribute(Const.CUREENT_USER);
        if (null==user){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查是否是管理员
        if(iUserService.checkRoleAdmin(user).isSuccess()){
            //查询的子节点的category 信息并且递归查询 1==>100==>1000
             return  iCatecoryService.selectCategoryAndChildrenById(categoryId);
        }else{
            return  ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }



























}
