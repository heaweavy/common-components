package com.github.heaweavy.common.components.common;

/**
 * @author caimb
 * @date Created at 2019-06-17 17:40
 * @modefier
 */
public class JVMTest {
    public static Integer s1 = new Integer( 300 );
    public final Integer s2 = new Integer( 400 );
    public static final Integer s3 = new Integer( 500 );

    public static void main(String[] args) {
        JVMTest test = new JVMTest();
        int locals1 = s1 + test.s2;
        int locals2 = test.s2 + s3;
        int locals3 = s1 + s3;
        int locals4 = s1;
        int locals5 = test.s2;
        int locals6 = s3;
        int locals7 = locals4 + locals5 + locals6;
        System.out.println(locals1);
        System.out.println(locals2);
        System.out.println(locals3);
        System.out.println(locals7);
    }
}
