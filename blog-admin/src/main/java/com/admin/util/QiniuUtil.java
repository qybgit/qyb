package com.admin.util;

import com.alibaba.fastjson2.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 七牛云服务工具类
 */
@Component
public class QiniuUtil {
    @Value("${access_key}")
    private String access_key;
    @Value("${secret_key}")
    private String secret_key;
    @Value("${bucket}")
    private String bucket;
    String key = null;//默认不指定key的情况下，以文件内容的hash值作为文件名
    public static final String url="image.yuanbolife.top";

    public boolean upload(MultipartFile file, String fileName) {
        //构造一个带指定 Region 对象的配置类//    cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        ////...其他参数参考类注释
        Configuration cfg = new Configuration(Region.regionAs0());
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        key = fileName;
        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(access_key, secret_key);
            String upToken = auth.uploadToken(bucket);

            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}