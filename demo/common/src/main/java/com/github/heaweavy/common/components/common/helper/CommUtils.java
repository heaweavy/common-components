package com.github.heaweavy.common.components.common.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共工具类
 * @author Roma
 * @datetime 2015/11/3 - 11:42
 */
public class CommUtils {

    /**
     * 判断输入字符串是否为手机号码格式
     * @param mobiles
     * @return
     */
    public static boolean isPhoneNumber(String mobiles) {
        if (mobiles == null) {
            return false;
        } else {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        }
    }

    public static void main(String[] args) {
        System.out.println(CommUtils.isPhoneNumber("13570295580"));
    }
}


