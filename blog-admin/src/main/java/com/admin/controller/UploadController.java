package com.admin.controller;



import com.admin.util.QiniuUtil;
import com.framework.vo.ImageResult;
import com.framework.vo.ImageVo;
import com.framework.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {
    @Resource
    QiniuUtil qiniuUtil;
    @PostMapping
    public ImageResult upload(@RequestParam("image")MultipartFile file){
        String fileName= UUID.randomUUID()+"."+ StringUtils.substringAfterLast(file.getOriginalFilename(),".");
        boolean upload=qiniuUtil.upload(file,fileName);
        if (upload){
            ImageVo imageVo=new ImageVo();
            imageVo.setUrl(QiniuUtil.url+"/"+fileName);
           return ImageResult.success(imageVo);
        }else{
            return ImageResult.error();
        }
    }
}
