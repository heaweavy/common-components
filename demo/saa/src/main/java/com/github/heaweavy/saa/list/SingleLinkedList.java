package com.github.heaweavy.saa.list;


/**
 * @author caimb
 * @date Created at 2019-07-03 8:43
 * @modefier
 */
public class SingleLinkedList<E> {

    private transient Node<E> head;
    private transient Node<E> tail;

    private transient int size;

    public SingleLinkedList() {
    }

    public void add(E e) {
        Node<E> node = new Node<>( e, null );
        if ( head == null ) {
            head = node;
        }
        if ( tail != null ) {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    public void add(int index, E e) {
        final int size = this.size;
        if ( index > size ) {
            throw new IndexOutOfBoundsException( "Index " + index + ", Size " + size );
        }
        if ( index == size ) {
            add( e );
        } else if(index == 0){
            Node<E> node = new Node<>( e, head );
            head = node;
        } else {
            Node<E> last = head;
            for ( int i = 1; i < size; ++i ) {
                if ( i == index ) {
                    Node<E> node = new Node<>( e, last.next );
                    last.next = node;
                } else {
                    last = last.next;
                }
            }
        }
        this.size++;
    }

    public E get(int index) {
        checkRange( index );
        Node<E> cur = head;
        for ( int i = 0; i < size; i++ ) {
            if ( i == index ) {
                return cur.e;
            } else {
                cur = cur.next;
            }
        }
        return null;
    }

    public void remove(E e) {
        final Node<E> head = this.head;
        Node<E> cur = head;
        Node<E> last = head;
        for ( ; ; ) {
            if ( cur.e.equals( e ) ) {
                if ( cur == head ) {
                    this.head = cur.next;
                    cur.next = null;
                    cur.e = null;
                    if ( size == 1 ) {
                        tail = null;
                    }
                } else if ( cur.next == null ) {
                    last.next = null;
                    this.tail = last;
                    cur.e = null;
                } else {
                    last.next = cur.next;
                    cur.next = null;
                    cur.e = null;
                }
                size--;
                break;
            }else {
                last = cur;
                cur = cur.next;
            }
        }
    }

    public E remove(int index) {
        checkRange( index );
        final Node<E> head = this.head;
        E e = null;
        if ( index == 0 ) {
            head.next = null;
            e = head.e;
            head.e = null;
            this.head = head.next;
            if ( size == 1 ) {
                tail = null;
            }
        }else {
            Node<E> cur = head.next, last = head;
            int i;
            for ( i = 1; i <= index; ++i ) {
                last = cur;
                cur = cur.next;
            }
            if ( i == size - 1 ) {
                tail = last;
            }
            last.next = cur.next;
            e = cur.e;
            cur.next = null;
            cur.e = null;
        }
        size--;
        return e;
    }

    public void reserve() {
        Node<E> cur = this.head, next, newLast = null;
        this.tail = head;
        for(;;) {
            next = cur.next;
            cur.next = newLast;
            newLast = cur;
            cur = next;
            if ( cur == null ) {
                this.head = newLast;
                break;
            }
        }
    }

    public void clear() {
        Node<E> cur = head, next;
        for ( ; cur != null; ) {
            cur.e = null;
            next = cur.next;
            cur.next = null;
            cur = next;
        }
        this.tail = null;
        this.head = null;
        size = 0;
    }

    public String toString() {
        final Node<E> head = this.head;
        Node<E> cur = head;
        StringBuilder sb = new StringBuilder( "[" );
        for ( ; cur != null; ) {
            if ( cur != head ) {
                sb.append( "," );
            }
            sb.append( cur.e );
            cur = cur.next;
        }
        sb.append( "]" );
        return sb.toString();
    }


    private void checkRange(int index) {
        if ( index >= size ) {
            throw new IndexOutOfBoundsException( "Index " + index + ", Size " + size );
        }
    }

    private static class Node<E>{
        E e;
        Node<E> next;

        Node(E e, Node<E> next) {
            this.e = e;
            this.next = next;
        }
    }
}
