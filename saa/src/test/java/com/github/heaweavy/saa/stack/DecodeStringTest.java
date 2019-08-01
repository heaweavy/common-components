package com.github.heaweavy.saa.stack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author caimb
 * @date Created at 2019-07-11 14:26
 * @modefier
 */
public class DecodeStringTest {
    @Test
    public void test() {
        DecodeString decodeString = new DecodeString();
        System.out.println( decodeString.decodeString( "3[a]2[bc]" ) );
        System.out.println( decodeString.decodeString( "3[a2[c]]" ) );
        System.out.println( decodeString.decodeString( "2[abc]3[cd]ef" ) );
        System.out.println( decodeString.decodeString( "2[qwe2[abc]3[cd]ef]2[vn]" ) );
    }


    @Test
    public void testHash() {
        AtomicInteger nextHashCode = new AtomicInteger();
        int HASH_INCREMENT = 0x61c88647;
        for(int i=0;i<20;i++) {
            System.out.println( nextHashCode.getAndAdd( HASH_INCREMENT ) );
        }
    }
}
