package com.github.heaweavy.common.components.common;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author caimb
 * @date Created at 2019-09-03 9:49
 * @modefier
 */
public class GenericsFun {
    public static void main(String[] args) {
        List<? extends Number> numbers = new LinkedList<Long>();
        Number i = numbers.get( 0 );
        List<? super Comparable> com = new LinkedList<>();
        TreeMap<String,String> treeMap = new TreeMap<>( new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        } );
    }

}
