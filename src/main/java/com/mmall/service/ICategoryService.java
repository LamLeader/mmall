package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * @description:
 * @author: 12484
 * @date: Created in 2020/1/6 22:42
 * @version: 1
 * @modified By:
 */
public interface ICategoryService {

    /**
     * @description  管理员添加商品分类
     * @author 12484
     * @date 2020/1/6 22:52
     * @param categoryName,parentId
     * @return
     */
    public ServerResponse addCategory(String categoryName,Integer parentId);
    /**
     * @description  管理员修改商品品类名称
     * @author 12484
     * @date 2020/1/6 23:03
     * @param categoryId,categoryName
     * @return
     */
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    /**
     * @description 根据当前分类查询子分类
     * @author 12484
     * @date 2020/1/6 23:37
     * @param categoryId
     * @return
     */
    public ServerResponse<List<Category>> getChildParelleCategory(Integer categoryId);
    /**
     * @description  递归查询本节点以及孩子节点的id
     * @author 12484
     * @date 2020/1/7 0:16
     * @param categoryId
     * @return com.mmall.common.ServerResponse
     */
    public  ServerResponse selectCategoryAndChildrenById(Integer categoryId);
}
