package com.github.heaweavy.saa.stack;

/**
 * leetcode
 * 150. 逆波兰表达式求值
 * @author caimb
 * @date Created at 2019-07-11 10:08
 * @modefier
 */
public class ReversePolish {
//    private static final int[] STACK = new int[10237];

    public int evalRPN(String[] tokens) {
        int[] STACK = new int[tokens.length];
        int top = -1;
        int cur;
        for ( String token : tokens ) {
            switch ( token ) {
                case "*":
                    cur = top;
                    STACK[--top] = STACK[top] * STACK[cur];
                    break;
                case "+":
                    cur = top;
                    STACK[--top] = STACK[top] + STACK[cur];
                    break;
                case "-":
                    cur = top;
                    STACK[--top] = STACK[top] - STACK[cur];
                    break;
                case "/":
                    cur = top;
                    STACK[--top] = STACK[top] / STACK[cur];
                    break;
                default:
                    STACK[++top] = Integer.valueOf( token );
            }
        }
        return STACK[top];
    }
    public static void main(String[] args) {
        System.out.println( (int) '*' );
        System.out.println( (int) '+' );
        System.out.println( (int) '-' );
        System.out.println( (int) '/' );
        System.out.println( (int) '0' );
        System.out.println( (int) '9' );
    }
}
