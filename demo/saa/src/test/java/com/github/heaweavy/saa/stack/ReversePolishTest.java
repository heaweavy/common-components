package com.github.heaweavy.saa.stack;

import org.junit.Test;

/**
 * @author caimb
 * @date Created at 2019-07-11 10:22
 * @modefier
 */
public class ReversePolishTest {
    @Test
    public void test() {
        ReversePolish reversePolish = new ReversePolish();
//        System.out.println( reversePolish.evalRPN( new String[]{"2", "1", "+", "3", "*"} ) );
//        System.out.println( reversePolish.evalRPN( new String[]{"4", "13", "5", "/", "+"} ) );
        System.out.println( reversePolish.evalRPN( new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"} ) );

        long start = System.currentTimeMillis();
        for(int i=0;i<100000;i++) {
            reversePolish.evalRPN( new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"} );
        }
        System.out.println( System.currentTimeMillis() - start );
    }
}
