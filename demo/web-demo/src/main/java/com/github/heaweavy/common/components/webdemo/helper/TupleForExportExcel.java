package com.github.heaweavy.common.components.webdemo.helper;

import java.util.List;

/**
 * Created by 2 on 2015/10/27.
 */
public class TupleForExportExcel {
    private String sheetName;
    private List<String> headers;
    private List<String> MethodNames;
    private List data;
    public TupleForExportExcel() {
    }
    public TupleForExportExcel(String sheetName, List<String> headers , List<String> methodNames, List data) {
        this.sheetName = sheetName;
        this.data = data;
        MethodNames = methodNames;
        this.headers = headers;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public String getSheetName() {
        return sheetName;

    }


    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<String> getMethodNames() {
        return MethodNames;
    }

    public void setMethodNames(List<String> methodNames) {
        MethodNames = methodNames;
    }
}
