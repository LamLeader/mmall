package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.utils.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

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

    @Autowired
    private IFileService iFileService;

    /**
     * @param product
     * @return com.mmall.common.ServerResponse
     * @description 新增或者修改产品(根据product 的id 判断)
     * @author 12484
     * @date 2020/1/7 23:49
     */
    @RequestMapping(value = "saveOrupdateProduct.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse saveOrupdateProduct(Product product, HttpSession session) {
        User user = (User) session.getAttribute(Const.CUREENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        //检查是否是管理员
        if (iUserService.checkRoleAdmin(user).isSuccess()) {
            return iProductService.saveOrupdateProduct(product);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * @param productId, status
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @descriptionc 产品的上下架
     * @author 12484
     * @date 2020/1/8 0:08
     */
    @RequestMapping(value = "setProductStatus.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> setProductStatus(Integer productId, Integer status, HttpSession session) {
        User user = (User) session.getAttribute(Const.CUREENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        //检查是否是管理员
        if (iUserService.checkRoleAdmin(user).isSuccess()) {
            //业务逻辑修改状态（上下架）
            return iProductService.setProductStatus(productId, status);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * @param productId
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @descriptionc 获取产品详情
     * @author 12484
     * @date 2020/1/8 0:08
     */
    @RequestMapping(value = "getProductDetail.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getProductDetail(Integer productId, HttpSession session) {
        User user = (User) session.getAttribute(Const.CUREENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        //检查是否是管理员
        if (iUserService.checkRoleAdmin(user).isSuccess()) {
            return iProductService.manageProductDetail(productId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * @param pageNum,pageSize
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @descriptionc 获取产品列表
     * @author 12484
     * @date 2020/1/8 0:08
     */
    @RequestMapping(value = "getProductList.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getProductList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, HttpSession session) {
        User user = (User) session.getAttribute(Const.CUREENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        //检查是否是管理员
        if (iUserService.checkRoleAdmin(user).isSuccess()) {
            return iProductService.getProductList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * @param pageNum,pageSize
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @descriptionc 获取产品列表
     * @author 12484
     * @date 2020/1/8 0:08
     */
    @RequestMapping(value = "searchProductList.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse searchProductList(
            String productName, Integer productId,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, HttpSession session) {
        User user = (User) session.getAttribute(Const.CUREENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        //检查是否是管理员
        if (iUserService.checkRoleAdmin(user).isSuccess()) {
            return iProductService.searchProductList(productName, productId, pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * @param file  http://localhost:8088/mmall_war/manage/product/uploadFile.do
     * @param request
     * @return com.mmall.common.ServerResponse
     * @description 文件上传
     * @author 12484
     * @date 2020/1/9 22:17
     */
    @RequestMapping(value = "uploadFile.do")
    @ResponseBody
    public ServerResponse uploadFile(HttpSession session, @RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request) {

        User user = (User) session.getAttribute(Const.CUREENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        //检查是否是管理员
        if (iUserService.checkRoleAdmin(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.uploadFile(file, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }

    }

    /**
     * @param file
     * @param request
     * @return com.mmall.common.ServerResponse
     * @description 富文本文件上传
     * @author 12484
     * @date 2020/1/9 22:17
     */
    @RequestMapping(value = "richTextFileUpload.do", method = RequestMethod.POST)
    @ResponseBody
    public Map richTextFileUpload(HttpSession session, @RequestParam(value = "uploadFile", required = false) MultipartFile file,
                                  HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();
        User user = (User) session.getAttribute(Const.CUREENT_USER);
        if (null == user) {
            resultMap.put("success", false);
            resultMap.put("msg", "请登陆管理员");
            return resultMap;
        }
        //富文本中对于返回富文本的  我们使用的是simditor 需要根据 对应格式返回
        //检查是否是管理员
        if (iUserService.checkRoleAdmin(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.uploadFile(file, path);
            if (StringUtils.isBlank(targetFileName)) {
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            Map fileMap = Maps.newHashMap();
            resultMap.put("success", targetFileName);
            resultMap.put("msg", "上传成功");
            resultMap.put("url", url);
            response.addHeader("Access-Control-Allow-Headers","X-file-Name");
            return resultMap;
        } else {
            resultMap.put("success", false);
            resultMap.put("msg", "无权限操作，需要管理员权限");
            return resultMap;
        }

    }

}
