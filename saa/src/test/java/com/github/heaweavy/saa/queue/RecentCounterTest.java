package com.github.heaweavy.saa.queue;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * @author caimb
 * @date Created at 2019-07-09 14:53
 * @modefier
 */
public class RecentCounterTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        RecentCounter rc = new RecentCounter();
        Scanner sc = new Scanner( System.in );
        int ping;
        System.out.println( "请输入：\n" );
        while ( (ping = sc.nextInt()) != -1 ) {
            System.out.println( rc.ping( ping ) );
        }
        sc.close();
    }

    @Test
    public void test3000() {
        RecentCounter rc = new RecentCounter();
        int i;
        for ( i = 0; i < 3000; i++ ) {
            rc.ping( i );
        }
        rc.ping( i++ );
        rc.ping( i++ );
        System.out.println( rc.ping( i ) );
    }
    @Test
    public void linkTest3000() {
        RecentCounterLink rc = new RecentCounterLink();
        int i;
        for ( i = 0; i < 3000; i++ ) {
            rc.ping( i );
        }
        rc.ping( i++ );
        rc.ping( i++ );
        System.out.println( rc.ping( i ) );
    }
}
