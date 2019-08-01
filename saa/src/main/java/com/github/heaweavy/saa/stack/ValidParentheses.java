package com.github.heaweavy.saa.stack;

/**
 * leetcode
 * 20. 有效括号
 * @author caimb
 * @date Created at 2019-07-11 8:35
 * @modefier
 */
public class ValidParentheses {
    private static final int[] stack = new int[1024];

    public boolean isValid(String s) {
        int top = -1, len = s.length();
        if ( (len & 1) == 1 ) {
            return false;
        }
        char c;
        int right5;
        for ( int i = 0; i < len; i++ ) {
            c = s.charAt( i );
            right5 = c >> 5;
            if ( top != -1 ) {
                if ( stack[top] == (right5) ) {
                    if ( (c & 3) == 1 ) {
                        top--;
                        continue;
                    }
                }
            }
            if ( (c & 3) == 1 ) {
                return false;
            }else {
                stack[++top] = right5;
            }
        }
        return top == -1;
    }

    public static void main(String[] args) {
        System.out.println((int)'(');//010 1000
        System.out.println((int)'[');//101 1011
        System.out.println((int)'{');//111 1011
        System.out.println((int)')');//010 1001‬
        System.out.println((int)']');//101 1101‬
        System.out.println((int)'}');//111 1101
        System.out.println((int)' ');//010 0000
        System.out.println( ' ' >> 5 );
        System.out.println( '(' & 3 );
        System.out.println( '[' & 3 );
        System.out.println( '{' & 3 );
        System.out.println( ')' & 3 );
        System.out.println( ']' & 3 );
        System.out.println( '}' & 3 );
    }
}
