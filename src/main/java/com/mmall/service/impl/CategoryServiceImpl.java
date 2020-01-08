package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: 12484
 * @date: Created in 2020/1/6 22:43
 * @version: 1
 * @modified By:
 */
@Service(value = "iCatecoryService")
public class CategoryServiceImpl  implements ICategoryService {

    private Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {

        if(StringUtils.isBlank(categoryName)||parentId==null){
             return ServerResponse.createBySuccessMessage("添加商品品类参数错误");
        }
        Category category=new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int resultCount=categoryMapper.insert(category);
        if (resultCount>0){
            return  ServerResponse.createBySuccessMessage("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if(StringUtils.isBlank(categoryName)||categoryId==null){
            return ServerResponse.createBySuccessMessage("更新商品品类参数错误");
        }
        Category category=new Category();
        category.setName(categoryName);
        category.setId(categoryId);

        int resultCount=categoryMapper.updateByPrimaryKeySelective(category);
        if (resultCount>0){
            return  ServerResponse.createBySuccessMessage("修改品类名称成功");
        }
        return ServerResponse.createByErrorMessage("修改品类名称失败");
    }


    public ServerResponse<List<Category>> getChildParelleCategory(Integer categoryId){
        List<Category> categoryList=categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    public  ServerResponse selectCategoryAndChildrenById(Integer categoryId){
        Set<Category> categorySet= Sets.newHashSet();//初始化hashSet();
        findChildCategory(categorySet,categoryId);
        List<Integer> categoryList= Lists.newLinkedList();
        if (null!=categoryList){
            for (Category categoryItem:categorySet){
                categoryList.add(categoryItem.getId());
            }
        }
        return  ServerResponse.createBySuccess(categoryList);
    }
    /**
     * @description   递归算法（Set 去重无序）
     * @author 12484
     * @date 2020/1/7 0:00
     * @param categorySet
     * @param categoryId
     * @return java.util.Set<com.mmall.pojo.Category>
     */
    public Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId){
        Category category=categoryMapper.selectByPrimaryKey(categoryId);
        if (null!=category){
            categorySet.add(category);
        }
        //查找子节点 递归算法一定有一个退出条件
        List<Category> categoryList=categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category categoryItem:categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;

    }



}
