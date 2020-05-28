package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.utils.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @description:
 * @author: 12484
 * @date: Created in 2020/1/9 21:39
 * @version: 1
 * @modified By:
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private static  final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) {
        String fileName=file.getOriginalFilename();
        //获取扩展名 如 adc.jpg
        String fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName= UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名:{},上传的路径:{},新文件名:{},",fileName,path,uploadFileName);
        File fileDir=new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);//赋予写的权限
            fileDir.mkdirs();
        }
        File targetFile=new File(path,uploadFileName);
        try {
            //文件已经上传成功
            file.transferTo(targetFile);
            //todo 讲targetFile上传到FTP服务器上
            FTPUtil.uploadFile(Lists.<File>newArrayList(targetFile));
            //上传完之后删除upload下的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.info("文件上传异常",e);
            return  null;
        }
        return targetFile.getName();
    }
}
