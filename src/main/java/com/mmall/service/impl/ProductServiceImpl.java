package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import com.mmall.utils.DateTimeUtil;
import com.mmall.utils.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private CategoryMapper categoryMapper;

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

    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if (null==productId){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"产品参数错误");
        }
        Product product=productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.createByErrorMessage("产品已下架或者被删除");
        }
        ProductDetailVo productDetailVo=assembleProductDetailVo(product);
        return  ServerResponse.createBySuccess(productDetailVo);

    }


    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);//默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        //startPage--start
        PageHelper.startPage(pageNum,pageSize);
        //填充自己的sql查询逻辑
        List<Product> productList=productMapper.selectList();
        List<ProductListVo> productListVoList= Lists.newArrayList();
        for (Product product:productList){
            ProductListVo productListVo=assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        //pageHelper-首尾
        PageInfo pageResult=new PageInfo(productList);
        pageResult.setList(productList);
        return ServerResponse.createBySuccess(pageResult);
    }
    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }
    @Override
    public ServerResponse<PageInfo> searchProductList(String productName, Integer productId, Integer pageNum, Integer pageSize) {
        //startPage--start
        PageHelper.startPage(pageNum,pageSize);
        //填充自己的sql查询逻辑
        List<Product> productList=productMapper.selectByNameAndProductId(productName,productId);
        List<ProductListVo> productListVoList= Lists.newArrayList();//list 声明
        for (Product product:productList){
            ProductListVo productListVo=assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        //pageHelper-首尾
        PageInfo pageResult=new PageInfo(productList);
        pageResult.setList(productList);
        return ServerResponse.createBySuccess(pageResult);

    }
}
