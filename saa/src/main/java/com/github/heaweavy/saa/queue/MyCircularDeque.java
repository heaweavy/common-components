package com.github.heaweavy.saa.queue;

/**
 * leetcode
 * 641. 设计循环双端队列
 * @author caimb
 * @date Created at 2019-07-09 16:59
 * @modefier
 */
public class MyCircularDeque {
    int capacity;
    int size;
    Node front;
    Node rear;

    /** Initialize your data structure here. Set the size of the deque to be k. */
    public MyCircularDeque(int k) {
        capacity = k;
        size = 0;
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    public boolean insertFront(int value) {
        if ( isFull() ) {
            return false;
        }
        Node f = front;
        if ( f != null ) {
            f = new Node( null, value, f );
            f.next.prev = f;
            front = f;
        }else {
            front = new Node( null, value, null );
            rear = front;
        }
        size++;
        return true;
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    public boolean insertLast(int value) {
        if ( isFull() ) {
            return false;
        }
        Node r = rear;
        if ( r != null ) {
            r = new Node( r, value, null );
            r.prev.next = r;
            rear = r;
        } else {
            rear = new Node( null, value, null );
            front = rear;
        }
        size++;
        return true;
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if ( size == 0 ) {
            return false;
        }
        front = front.next;
        if ( front == null ) {
            rear = null;
        }else {
            front.prev = null;
        }
        size--;
        return true;
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    public boolean deleteLast() {
        if ( size == 0 ) {
            return false;
        }
        rear = rear.prev;
        if ( rear == null ) {
            front = null;
        } else {
            rear.next = null;
        }
        size--;
        return true;
    }

    /** Get the front item from the deque. */
    public int getFront() {
        if ( size == 0 ) {
            return -1;
        }
        return front.data;
    }

    /** Get the last item from the deque. */
    public int getRear() {
        if ( size == 0 ) {
            return -1;
        }
        return rear.data;
    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        return size == capacity;
    }

    private static class Node {
        int data;
        Node prev;
        Node next;

        public Node(Node prev, int data, Node next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
}
