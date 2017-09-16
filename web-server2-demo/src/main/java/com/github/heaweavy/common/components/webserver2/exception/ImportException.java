package com.github.heaweavy.common.components.webserver2.exception;

/**
 * 导入异常类
 * Created by zhangtao on 15/10/24.
 */
public class ImportException extends RuntimeException{;
    private final String messTemp ="sheet: %s  ,row: %d , 原因：%s";
    private String message ="" ;

    public ImportException(String sheetName, int rownum, String reason){
        super();
        message=String.format(messTemp,sheetName,rownum,reason);
    }

    public ImportException(String reason){
        super();
        message=String.format(reason);
    }

    public ImportException(){
        super();
    }

    @Override
    public String getMessage(){
        return message;
    }


}