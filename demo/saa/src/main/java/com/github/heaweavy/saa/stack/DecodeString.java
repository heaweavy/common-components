package com.github.heaweavy.saa.stack;

/**
 * leetcode
 * 394. 字符串解码
 *
 * @author caimb
 * @date Created at 2019-07-11 13:40
 * @modefier
 */
public class DecodeString {
    public String decodeString(String s) {
        StringBuilder sb = new StringBuilder();
        decode( s, sb, 0, true );
        return sb.toString();
    }

    private int decode(String s, StringBuilder sb, final int index, boolean isRoot) {
        int num, i = index, len = s.length();
        String numStr = "";
        char c;
        while ( i < len ) {
            c = s.charAt( i );
            if ( c < 58 ) {
                numStr += c;
            } else if ( c == '[' ) {
                StringBuilder tmp = new StringBuilder();
                i = decode( s, tmp, i + 1, false );
                if ( !numStr.isEmpty() ) {
                    num = Integer.valueOf( numStr );
                } else {
                    num = 1;
                }
                while ( num-- != 0 ) {
                    sb.append( tmp );
                }
                numStr = "";
            } else if ( c == ']' ) {
                if ( isRoot ) {
                    numStr = "";
                }else {
                    break;
                }
            } else {
                sb.append( c );
            }
            i++;
        }
        return i;
    }

    public static void main(String[] args) {
        System.out.println( (int) '0' );
        System.out.println( (int) '9' );
        System.out.println( (int) '[' );
        System.out.println( (int) ']' );
        System.out.println( (int) 'a' );
        System.out.println( (int) 'z' );
        System.out.println( (int) 'A' );
        System.out.println( (int) 'Z' );
        System.out.println( 1 + .5 );
    }
}
