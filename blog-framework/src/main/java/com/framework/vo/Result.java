package com.framework.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private int code;
    private String msg;
    private Object data;
    public static Result success(Object data){
        return new Result(200,"success",data);
    }
    public static Result fail(int code,String msg,Object object){
        return new Result(code,msg,object);
    }
}
