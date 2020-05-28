package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @description:
 * @author: 12484
 * @date: Created in 2020/1/9 21:38
 * @version: 1
 * @modified By:
 */
public interface IFileService {

    /**
     * @description  文件上传封装类
     * @author 12484
     * @date 2020/1/9 21:40
     * @param file,path
     * @return
     */
    public  String uploadFile(MultipartFile file,String path);

}
