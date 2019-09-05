package com.github.heaweavy.saa.list;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

/**
 * @author caimb
 * @date Created at 2019-07-03 10:45
 * @modefier
 */
public class ListTest {
    private static final int elementCount = 10;
    private HArt<Integer> hArt;
    private SingleLinkedList<Integer> singleLinkedList;
    private DoubleLinkedList<Integer> doubleLinkedList;

    @Before
    public void init() {
        hArt = new HArt<>();
        singleLinkedList = new SingleLinkedList();
        doubleLinkedList = new DoubleLinkedList<>();
    }

    @Test
    public void testAdd() {

        long start = System.currentTimeMillis();
        for ( int i = 0; i < elementCount; i++ ) {
            hArt.add( i );
        }
        System.out.println( "HArt cost : " + (System.currentTimeMillis() - start) );
        start = System.currentTimeMillis();
        for ( int i = 0; i < elementCount; i++ ) {
            singleLinkedList.add( i );
        }
        System.out.println( "SingleLinkedList cost : " + (System.currentTimeMillis() - start) );
//        System.out.println(hArt);
//        System.out.println(singleLinkedList);
    }

    @Test
    public void testAddIndex() {
        for ( int i = 0; i < elementCount; i++ ) {
            hArt.add( i );
            singleLinkedList.add( i );
            doubleLinkedList.add( i );
        }
        for ( int i = 0, j = elementCount-1; i < elementCount; i++, j-- ) {
            hArt.add( j, i );
            singleLinkedList.add( j, i );
            doubleLinkedList.add( j, i );
        }
        System.out.println(hArt);
        System.out.println(singleLinkedList);
        System.out.println(doubleLinkedList);
    }

    @Test
    public void testReverse() {
        for ( int i = 0; i < elementCount; i++ ) {
            hArt.add( i );
            singleLinkedList.add( i );
            doubleLinkedList.add( i );
        }
        hArt.reverse();
        singleLinkedList.reserve();
        doubleLinkedList.reserve();
        System.out.println(hArt);
        System.out.println(singleLinkedList);
        System.out.println(doubleLinkedList);
    }

    @Test
    public void testSort() {
        doubleLinkedList.add( 10 );
        doubleLinkedList.add( 3 );
        doubleLinkedList.add( 8 );
        doubleLinkedList.add( 3 );
        doubleLinkedList.add( 5 );
        doubleLinkedList.add( 2 );
        /*doubleLinkedList.sort( new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo( o2 );
            }
        } );*/
        System.out.println( doubleLinkedList );
    }
}
