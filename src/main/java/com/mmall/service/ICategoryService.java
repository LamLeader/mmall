package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * @description:
 * @author: 12484
 * @date: Created in 2020/1/6 22:42
 * @version: 1
 * @modified By:
 */
public interface ICatecoryService {

    public ServerResponse addCategory(String categoryName,Integer parentId);
}
