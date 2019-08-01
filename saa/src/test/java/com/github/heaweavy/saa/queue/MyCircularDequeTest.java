package com.github.heaweavy.saa.queue;

import org.junit.Test;

/**
 * @author caimb
 * @date Created at 2019-07-09 17:45
 * @modefier
 */
public class MyCircularDequeTest {
    @Test
    public void testLink() {
        MyCircularDeque deque = new MyCircularDeque( 41 );
        deque.insertFront( 70 );
        deque.insertLast( 11 );
        System.out.println( deque.getRear() );
        System.out.println( deque.getFront() );
        System.out.println( deque.getFront() );
        System.out.println( deque.deleteLast() );
        System.out.println( deque.deleteFront() );
        System.out.println( deque.getRear() );
        System.out.println( deque.insertFront( 49 ) );
        System.out.println( deque.insertLast( 27 ) );
        System.out.println( deque.isEmpty() );
        System.out.println( deque.getFront() );
    }

    @Test
    public void testArray() {
        MyCircularDeque_Array deque = new MyCircularDeque_Array( 5 );

        deque.insertFront( 7 );
        deque.insertLast( 0 );
        System.out.println( deque.getFront() );
        System.out.println( deque.insertLast( 3 ) );
        System.out.println( deque.getFront() );
        System.out.println( deque.insertFront( 9 ) );
        System.out.println( deque.getRear() );
        System.out.println( deque.getFront() );
        System.out.println( deque.getFront() );
        System.out.println( deque.deleteLast() );
        System.out.println( deque.getRear() );
    }
}
