package com.github.heaweavy.common.components.common.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by caimb on 2017/2/23.
 */
public class ArrayUtil {
    public static <T> List<T> array2ArrayList(T[] ts) {
        if (ts == null) {
            return null;
        }
        List<T> array = new ArrayList<T>(ts.length);
        for(int i =0,len = ts.length; i < len; ++i) {
            array.add(ts[i]);
        }
        return array;
    }

    public static <T> List<T> array2LinkedList(T[] ts) {
        if (ts == null) {
            return null;
        }
        List<T> array = new LinkedList<T>();
        for(int i =0,len = ts.length; i < len; ++i) {
            array.add(ts[i]);
        }
        return array;
    }

    public static <T> T[] list2Array(List<T> list) {
        if (list == null) {
            return null;
        }
        Object[] array = new Object[list.size()];
        Iterator<T> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            array[i] = it.next();
            i++;
        }
        return (T[]) array;
    }
}
