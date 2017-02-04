package com.github.heaweavy.common.components.webdemo.helper;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 2 on 2015/10/27.
 */
public class ExcelUtils {

    /**
     * excel 版本为 2003
     * */
    public static Workbook exportExcel (List<TupleForExportExcel> tupleForExportExcels) {
        HSSFWorkbook workbook = new HSSFWorkbook();   //创建一个excel
        CellStyle style = workbook.createCellStyle();        //设置表头的类型
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        Font font = workbook.createFont();//设置字体
        Sheet sheet = null;
        Header header = null;
        Row row = null;
        Cell cell = null;

        for (TupleForExportExcel tuple : tupleForExportExcels) {
            sheet = workbook.createSheet(tuple.getSheetName());        //创建一个sheet
            List<String> headers = tuple.getHeaders();
            row = sheet.createRow(0);  //创建第0行
            for (int k = 0; k < headers.size(); k++) {
                cell = row.createCell(k);//创建第0行第k列
                cell.setCellValue(headers.get(k));//设置第0行第k列的值
                cell.setCellType(Cell.CELL_TYPE_STRING);
                sheet.setColumnWidth(k, headers.get(k).length() * 1000);//设置列的宽度
                font.setColor(XSSFFont.COLOR_NORMAL);      // 设置单元格字体的颜色.
                font.setFontHeight((short) 250); //设置单元字体高度
                style.setFont(font);//设置字体风格
                cell.setCellStyle(style);
            }
            //写入数据
            if(tuple.getMethodNames() == null || tuple.getData() == null) {
                continue;
            }
            List<String> MethodNames = tuple.getMethodNames();
            int i = 1;
            for (Object o : tuple.getData()) {
                int j = 0;
                row = sheet.createRow(i);
                for (; j < MethodNames.size(); j++) {
                    cell = row.createCell(j);
                    setCell(cell, o, MethodNames.get(j));
                    font.setColor(XSSFFont.COLOR_NORMAL);      // 设置单元格字体的颜色
                    font.setFontHeight((short) 250); //设置单元字体高度
                    style.setFont(font);//设置字体风格
                    cell.setCellStyle(style);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                }
                i++;
            }
        }
        return workbook;
    }

    public static void setCell(Cell cell,Object o,String methodName){
        Method method= Utils.getDeclaredMethod(o,methodName);
        try {
            if(method!=null) {
                Object oo = method.invoke(o);
                String result = null;
                if (oo != null) {
                    result = oo.toString();
                }
                switch (methodName) {
                    case "getStudentType":
                        if("NON_RESIDENT".equals(result)){
                            result = "走读生";
                        }else if("SHUTTLE".equals(result)){
                            result = "接送";
                        }else if("SCHOOL_BUS".equals(result)){
                            result = "校车";
                        }else if("RESIDENT".equals(result)){
                            result = "住校生";
                        }else{
                            result = "所有";
                        }
                        break;
                    case "getGender" :
                        if ("MALE".equals(result)) {
                            result = "男";
                        } else if ("FEMALE".equals(result)) {
                            result = "女";
                        } else {
                            result = "其他";
                        }
                        break;
                    case "isValid" :
                        result = "true".equals(result) ? "是":"否";
                        break;
                    case "isAdmin" :
                        result = "true".equals(result) ? "是":"否";
                        break;
                    case "getWechat" :
                        result = result != null ? "是":"否";
                        break;
                }
                cell.setCellValue(result);
            } else {
                cell.setCellValue("");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Map<String,List> readExcel(InputStream excelIs,List<String> sheetNames) throws  Exception{
        Workbook workbook;
        try {
            workbook = WorkbookFactory.create(excelIs);
        } catch (IOException e){
            throw new RuntimeException("读取Excel文件失败: ", e);
        }

        workbook.setMissingCellPolicy(Row.RETURN_BLANK_AS_NULL);
        Sheet sheet = null;
        Row row = null;
        Map excelData = new HashMap();
        List<List> sheetDatas ;
        List<String> rowData ;
        int colNum =0;
        for(String sheetName : sheetNames){
            sheetDatas =new ArrayList<>();
            sheet = workbook.getSheet(sheetName);
            colNum = sheet.getRow(0).getLastCellNum();
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                row = sheet.getRow(rowNum);
                if(StringUtils.isEmpty(getCellValue(row,0)))break;
                rowData = new ArrayList<>();
                for (int i = 0; i < colNum; i++) {
                    rowData.add(getCellValue(row,i));
                }
                sheetDatas.add(rowData);
            }
            excelData.put(sheetName, sheetDatas);
        }
        return excelData;
    }

    //所有的cell按String处理，再按需转换
    private static String getCellValue(Row row, int celNum){
        Cell cell = row.getCell(celNum, Row.RETURN_BLANK_AS_NULL);
        if(null != cell){
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return cell.getStringCellValue();
        } else {
            return "";
        }
    }


}
