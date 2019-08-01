package com.github.heaweavy.saa.list;

/**
 * @author caimb
 * @date Created at 2019-07-02 10:43
 * @modefier
 */
public class HArt<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private transient Object[] item;
    private transient int size;

    public HArt(int capacity) {
        item = new Object[capacity];
    }

    public HArt() {
        item = EMPTY_ELEMENTDATA;
    }

    public void add(T t) {
        if ( size + 1 > item.length ) {
            grow();
        }
        item[size++] = t;
    }

    public void add(int index, T t) {
        checkRange( index - 1 );
        if ( size + 1 > item.length ) {
            grow();
        }
        int moveNum = size - index;
        System.arraycopy( item, index, item, index + 1, moveNum );
        item[index] = t;
        size++;
    }

    private void grow() {
        if ( item == EMPTY_ELEMENTDATA ) {
            item = new Object[DEFAULT_CAPACITY];
            return;
        }
        int minCapacity = size + 1;
        if ( minCapacity < 0 ) {// overflow
            throw new OutOfMemoryError();
        }
        int oldCapacity = item.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if ( newCapacity - minCapacity < 0 ) {
            newCapacity = minCapacity;
        }
        Object[] copy = new Object[newCapacity];
        System.arraycopy( item, 0, copy, 0, item.length );
        item = copy;
    }

    public T remove(int index) {
        checkRange( index );
        T t = (T) item[index];
        int newLength = size - 1;
        for ( int i = index; i < newLength;) {
            item[i] = item[++i];
        }
        item[size = newLength] = null;
        return t;
    }

    public T get(int index) {
        checkRange( index );
        return (T) item[index];
    }

    public void reverse() {
        Object tmp;
        for ( int i = 0, j = size - 1; i < j; ++i, --j ) {
            tmp = item[i];
            item[i] = item[j];
            item[j] = tmp;
        }
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        for ( int i = 0; i < size; i++ ) {
            item[i] = null;
        }
        item = EMPTY_ELEMENTDATA;
        size = 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for ( int i = 0; i < size; i++ ) {
            if ( i != 0 ) {
                sb.append( "," );
            }
            sb.append( item[i] );
        }
        sb.append( "]" );
        return sb.toString();
    }

    private void checkRange(int index) {
        if ( index >= size ) {
            throw new IndexOutOfBoundsException( "Index: " + index + ", Size: " + size );
        }
    }
}
