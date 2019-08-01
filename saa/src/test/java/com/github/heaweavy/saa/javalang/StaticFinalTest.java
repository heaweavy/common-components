package com.github.heaweavy.saa.javalang;

/**
 * @author caimb
 * @date Created at 2019-07-30 8:38
 * @modefier
 */
public class StaticFinalTest {
    public static final int sfc = 123;
    public static final int sfl;
    public static int ss = 789;
    public static volatile int svt = 12121;
    static {
        sfl = 456;
    }

    public static void main(String[] args) {
        int lv = 222222222;
        lv = sfl;
        lv = ss;
        ss = 123456;
        svt++;
        System.out.println( lv );
    }
}
