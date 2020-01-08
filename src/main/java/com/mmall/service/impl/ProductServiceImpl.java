package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: 12484
 * @date: Created in 2020/1/7 23:52
 * @version: 1
 * @modified By:
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse saveOrupdateProduct(Product product) {
        int resultCount=0;
        if (StringUtils.isNotBlank(product.getSubImages())){
            String [] arrMainImg=product.getSubImages().split(",");
            product.setMainImage(arrMainImg[0]);//取出副图的第一张图片为主图
        }
        if(null!=product.getId()){
            resultCount= productMapper.updateByPrimaryKeySelective(product);
           if (resultCount>0){
               return ServerResponse.createBySuccessMessage("产品更新成功");
           }
        }else {
            resultCount=productMapper.insertSelective(product);
            if (resultCount>0){
                return ServerResponse.createBySuccessMessage("产品添加成功");
            }
        }
        return ServerResponse.createByErrorMessage("添加或者更新产品的参数不正确");
    }

    @Override
    public ServerResponse<String> setProductStatus(Integer productId,Integer status) {
        int resultCount=0;
        if (null==productId&&null==status){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"产品参数错误");
        }
        Product product=new Product();
        product.setId(productId);
        product.setStatus(status);
        resultCount=productMapper.updateByPrimaryKeySelective(product);
        if (resultCount>0){
            return ServerResponse.createBySuccess("修改产品销售状态成功");
        }
        return ServerResponse.createBySuccess("修改产品销售状态失败");
    }


}
