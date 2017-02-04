/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schoolguard.commander.helper;

import com.schoolguard.common.school.entity.GradeClass;
import com.schoolguard.common.school.entity.SchoolGrade;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 *
 * @author Rogers
 */
public class Utils {

    public static String randomString(int length){
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for(int i=0; i< length; i++){
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        
        return sb.toString();
    }

    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Method getDeclaredMethod(Object object, String methodName, Class<?> ... parameterTypes){
        Method method = null ;
        for(Class<?> clazz = object.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes) ;
                return method ;
            } catch (Exception e) {
            }
        }
        return null;
    }

    //班级排序
    public static List<GradeClass> sortClass(List<GradeClass> list) {
        Collections.sort(list, new Comparator<GradeClass>() {
            @Override
            public int compare(GradeClass g1, GradeClass g2) {
                if (g1.getSectionType().equals(g2.getSectionType())) {
                    if (g1.getStartYear().equals(g2.getStartYear())) {
                        return g1.getName().compareTo(g2.getName());
                    } else {
                        return g2.getStartYear().compareTo(g1.getStartYear());
                    }
                } else {
                    return g1.getSectionType().compareTo(g2.getSectionType());
                }
            }
        });
        return list;
    }

    //年级排序
    public static List<SchoolGrade> sortGrade(List<SchoolGrade> list) {
        Collections.sort(list, new Comparator<SchoolGrade>() {
            @Override
            public int compare(SchoolGrade g1, SchoolGrade g2) {
                if (g1.getSection().getSectionType() == g2.getSection().getSectionType()) {
                    return g2.getStartYear() - g1.getStartYear();
                } else {
                    return g1.getSection().getSectionType() - g2.getSection().getSectionType();
                }
            }
        });
        return list;
    }

    /**
     * @Author lihaopeng
     * @Date 2016/9/1 14:38
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
