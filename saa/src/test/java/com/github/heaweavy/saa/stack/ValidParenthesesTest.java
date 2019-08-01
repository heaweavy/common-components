package com.github.heaweavy.saa.stack;

import org.junit.Test;

/**
 * @author caimb
 * @date Created at 2019-07-11 9:31
 * @modefier
 */
public class ValidParenthesesTest {
    @Test
    public void test() {
        ValidParentheses validParentheses = new ValidParentheses();
        System.out.println( validParentheses.isValid( "((" ) );
        System.out.println( validParentheses.isValid( "()[]{}" ) );
        System.out.println( validParentheses.isValid( "(]" ) );
        System.out.println( validParentheses.isValid( "()" ) );
        System.out.println( validParentheses.isValid( "(){[((((()))))]}[[{{(())}}]]" ) );
    }
}
