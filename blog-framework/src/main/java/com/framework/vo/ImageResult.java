package com.framework.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageResult {
    private int errno;
    private Object data;
    public String msg;
    public static ImageResult success(Object url){
        return new ImageResult(0,url,"上传成功");
    }
    public static ImageResult error( ){
        return new ImageResult(1,null,"上传失败");
    }
}
