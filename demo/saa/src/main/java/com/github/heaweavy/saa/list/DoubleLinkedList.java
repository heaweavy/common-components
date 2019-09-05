package com.github.heaweavy.saa.list;

import java.util.Comparator;

/**
 * @author caimb
 * @date Created at 2019-07-03 14:42
 * @modefier
 */
public class DoubleLinkedList<E> {

    private transient int size;
    private transient Node<E> head;
    private transient Node<E> tail;

    public DoubleLinkedList() {
    }

    public void add(E e) {
        linkLast( e );
    }

    public void add(int index, E e) {
        checkRange( index );
        if ( index == 0 ) {
            linkFirst( e );
            return;
        } else if ( index == size ) {
            linkLast( e );
            return;
        }
        final int middle = size >> 1;
        if ( index < middle ) {
            Node<E> cur = head.next;
            for ( int i = 1; i < middle; ++i ) {
                if ( i == index ) {
                    linkNode( cur, e );
                    break;
                } else {
                    cur = cur.next;
                }
            }
        } else {
            Node<E> cur = tail;
            for ( int i = size - 1; i >= middle; --i ) {
                if ( i == index ) {
                    linkNode( cur, e );
                    break;
                } else {
                    cur = cur.prev;
                }
            }
        }
    }

    public void remove(E e) {
        final Node<E> h = head;
        final Node<E> t = tail;
        Node<E> cur = h;
        for ( ; cur != null; ) {
            if ( cur.e.equals( e ) ) {
                if ( cur == h ) {
                    cur.next.prev = null;
                    head = cur.next;
                    cur.next = null;
                    if ( size == 1 ) {
                        tail = null;
                    }
                } else if ( cur == t ) {
                    cur.prev.next = null;
                    tail = cur.prev;
                    cur.prev = null;
                } else {
                    cur.next.prev = cur.prev;
                    cur.prev.next = cur.next;
                    cur.prev = null;
                    cur.next = null;
                }
                cur.e = null;
                size--;
                break;
            } else {
                cur = cur.next;
            }
        }
    }

    public E remove(int index) {
        checkRange( index );
        E e = null;
        if ( index == 0 ) {
            final Node<E> h = head;
            e = head.e;
            h.e = null;
            h.next.prev = null;
            head = h.next;
            h.next = null;
        } else if ( index == size - 1 ) {
            final Node<E> t = tail;
            e = t.e;
            t.e = null;
            t.prev.next = null;
            tail = t.prev;
            t.prev = null;
        } else {
            final int middle = size >> 1;
            Node<E> cur;
            if ( index < middle ) {
                cur = head.next;
                for ( int i = 1; i < middle; i++ ) {
                    if ( i == index ) {
                        e = cur.e;
                        removeNode( cur );
                        break;
                    } else {
                        cur = cur.next;
                    }
                }
            } else {
                cur = tail.prev;
                for ( int i = size - 2; i >= middle; i-- ) {
                    if ( i == index ) {
                        e = cur.e;
                        removeNode( cur );
                        break;
                    }else {
                        cur = cur.prev;
                    }
                }
            }
        }
        size--;
        return e;
    }

    public E get(int index) {
        checkRange( index );
        final int middle = size >> 1;
        Node<E> cur;
        if ( index < middle ) {
            cur = head;
            for ( int i = 0; i < middle; i++ ) {
                if ( i == index ) {
                    return cur.e;
                } else {
                    cur = cur.next;
                }
            }
        } else {
            cur = tail;
            for ( int i = size - 1; i >= middle; i-- ) {
                if ( i == index ) {
                    return cur.e;
                } else {
                    cur = cur.prev;
                }
            }
        }
        return null;
    }

    public void reserve() {
        if ( size <= 1 ) {
            return;
        }
        Node cur = head, tmp;
        for(;cur != null;) {
            tmp = cur.prev;
            cur.prev = cur.next;
            cur.next = tmp;
            cur = cur.prev;
        }
        tmp = head;
        head = tail;
        tail = tmp;
    }


    public void clear() {
        if ( size == 0 ) {
            return;
        }
        Node cur = head;
        head = null;
        tail = null;
        size = 0;
        cur.e = null;
        cur = cur.next;
        for ( ; cur != null; ) {
            cur.e = null;
            cur.prev.next = null;
            cur.prev = null;
            cur = cur.next;
        }
    }

    public void linkNode(Node<E> cur, E e) {
        Node<E> newNode = new Node<>( cur.prev, e, cur );
        cur.prev.next = newNode;
        cur.prev = newNode;
        size++;
    }

    public void removeNode(Node<E> remove) {
        remove.prev.next = remove.next;
        remove.next.prev = remove.prev;
        remove.prev = null;
        remove.next = null;
        remove.e = null;
    }
    public void linkLast(E e) {
        final Node<E> t = tail;
        Node<E> node = new Node<>( t, e, null );
        if ( t == null ) {
            head = node;
        } else {
            t.next = node;
        }
        tail = node;
        size++;
    }

    public void linkFirst(E e) {
        final Node<E> h = head;
        Node<E> node = new Node<>( null, e, h );
        if ( h == null ) {
            tail = node;
        } else {
            h.prev = node;
        }
        head = node;
        size++;
    }

    public String toString() {
        final Node h = head;
        Node cur = h;
        StringBuilder sb = new StringBuilder("[");
        for(;cur != null;) {
            if ( cur != h ) {
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

    private static class Node<E> {
        E e;
        Node<E> prev;
        Node<E> next;

        public Node(Node<E> prev, E e, Node<E> next) {
            this.e = e;
            this.prev = prev;
            this.next = next;
        }
    }
}
