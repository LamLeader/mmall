package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;

import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: 12484
 * @date: Created in 2020/1/7 23:52
 * @version: 1
 * @modified By:
 */
public interface IProductService {

    /**
     * @description  新增或者修改产品(根据product 的id 判断)
     * @author 12484
     * @date 2020/1/7 23:49
     * @param product
     * @return com.mmall.common.ServerResponse
     */
    public ServerResponse saveOrupdateProduct(Product product);

    /**
     * @descriptionc 产品的上下架
     * @author 12484
     * @date 2020/1/8 0:08
     * @param  productId, status
     * @return com.mmall.common.ServerResponse<java.lang.String>
     */
    public ServerResponse<String> setProductStatus(Integer productId,Integer status);

}
