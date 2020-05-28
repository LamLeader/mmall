package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * @description  获取产品详情
     * @author 12484
     * @date 2020/1/8 22:58
     * @param productId
     * @return
     */
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    /**
     * @description 获取产品列表
     * @author 12484
     * @date 2020/1/8 23:10
     * @param pageNum,pageSize
     * @return
     */
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    /**
     * @description  获取产品列表 根据 productName,productId
     * @author 12484
     * @date 2020/1/8 23:25
     * @param  productName,productId
     * @return
     */
    public ServerResponse<PageInfo>  searchProductList(
            String productName,Integer productId,
           Integer pageNum,
           Integer pageSize);



}
