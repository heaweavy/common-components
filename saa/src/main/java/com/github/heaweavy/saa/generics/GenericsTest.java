package com.github.heaweavy.saa.generics;

import java.util.List;

/**
 * @author caimb
 * @date Created at 2019-07-15 16:24
 * @modefier
 */
public class GenericsTest {

/*
    @Test
    public void testSet() {
        ArrayList<? extends Number> list1 = new ArrayList<>();
        ArrayList<? super Number> list2 = new ArrayList<>();
        list1.add( null );
        list1.add( 1 );
        Object o = list1.get( 0 );
        Number n = list1.get( 0 );
        list2.add( null );
        list2.add( 1 );
        Object l2n1 = list2.get( 0 );
        Number l2n2 = list2.get( 0 );
        List list = new ArrayList();
        List<String> ls = list;
    }*/

    //上界
    public boolean test(List<? extends Number> list) {
        for ( Number number : list ) {
            System.out.println( number );
        }
        return true;
    }

    public void fileList(List<? super Number> list) {
        for ( int i = 0; i < 10; i++ ) {
            switch ( i % 3 ) {
                case 0:
                    list.add( new Integer( i ) );
                    break;
                case 1:
                    list.add( new Double( i ) );
                    break;
                case 2:
                    list.add( new Long( i ) );
                    break;
            }
        }
    }

/*    private static class Node<T> {
        List<? extends T> list;
        List<? super T> list2;

        public Node() {
            list = new ArrayList<>();
            list2 = new ArrayList<>();
        }

        public void add(T t) {
            list.add( t );
        }

        public T get(int i) {
            return list2.get( i );
        }
    }*/

}
