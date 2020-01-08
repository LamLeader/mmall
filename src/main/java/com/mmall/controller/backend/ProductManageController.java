package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
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
 * @date: Created in 2020/1/7 23:47
 * @version: 1
 * @modified By:
 */

@Controller
@RequestMapping(value = "/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;
    /**
     * @description  新增或者修改产品(根据product 的id 判断)
     * @author 12484
     * @date 2020/1/7 23:49
     * @param product
     * @return com.mmall.common.ServerResponse
     */
    @RequestMapping(value ="saveOrupdateProduct.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse saveOrupdateProduct(Product product, HttpSession session){
        User user=(User) session.getAttribute(Const.CUREENT_USER);
        if (null==user){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查是否是管理员
        if(iUserService.checkRoleAdmin(user).isSuccess()){
            return iProductService.saveOrupdateProduct(product);
        }else{
            return  ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }
    /**
     * @descriptionc 产品的上下架
     * @author 12484
     * @date 2020/1/8 0:08
     * @param  productId, status
     * @return com.mmall.common.ServerResponse<java.lang.String>
     */
    @RequestMapping(value ="setProductStatus.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> setProductStatus(Integer productId,Integer status,HttpSession session){
        User user=(User) session.getAttribute(Const.CUREENT_USER);
        if (null==user){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查是否是管理员
        if(iUserService.checkRoleAdmin(user).isSuccess()){
            return iProductService.setProductStatus(productId,status);
        }else{
            return  ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }






}
