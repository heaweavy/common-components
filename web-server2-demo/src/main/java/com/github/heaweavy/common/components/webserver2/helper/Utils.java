/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.heaweavy.common.components.webserver2.helper;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
}
