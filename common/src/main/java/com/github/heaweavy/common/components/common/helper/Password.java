package com.schoolguard.common.helper;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Rogers
 */
public class Password {
    private static final int logRounds = 10;


    /**
     * 计算bcrypt hashed密码
     * @param password
     * @return
     * @throws Exception 
     */
    public static String hash(String password){
        if(password == null || password.isEmpty()){
            throw new IllegalArgumentException("password is empty.");
        }
        return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
    }
    
    /**
     * 校验密码
     * @param password
     * @param stored
     * @return
     * @throws Exception 
     */
    public static boolean check(String password, String stored) throws Exception{
        if(password == null || password.isEmpty()){
            throw new IllegalArgumentException("password is empty.");
        }
        if(stored == null || stored.isEmpty()){
            throw new IllegalArgumentException("stored password is empty.");
        }

        return BCrypt.checkpw(password, stored);
    }


    public static void main(String[] args){
        String password = "123456";
        long start = System.currentTimeMillis();
        for(int i=0;i<50;i++){
            Password.hash(password);
        }
        long end = System.currentTimeMillis();
        System.out.println("程序运行时间："+(end-start)+"ms");
    }
}
