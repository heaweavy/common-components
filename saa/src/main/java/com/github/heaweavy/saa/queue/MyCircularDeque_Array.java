package com.github.heaweavy.saa.queue;

/**
 * leetcode
 * 641. 设计循环双端队列
 *
 * @author caimb
 * @date Created at 2019-07-09 16:59
 * @modefier
 */
public class MyCircularDeque_Array {
    static final int[] queue = new int[10000];
    int capacity, size, front, rear;

    /**
     * Initialize your data structure here. Set the size of the deque to be k.
     */
    public MyCircularDeque_Array(int k) {
        capacity = k;
        size = 0;
        front = rear = -1;
    }

    /**
     * Adds an item at the front of Deque. Return true if the operation is successful.
     */
    public boolean insertFront(int value) {
        if ( size >= capacity ) {
            return false;
        }
        int f = front;
        f = f == -1 ? 0 : (f - 1 + capacity) % capacity;
        rear = rear == -1 ? 0 : rear;
        queue[front = f] = value;
        size++;
        return true;
    }

    /**
     * Adds an item at the rear of Deque. Return true if the operation is successful.
     */
    public boolean insertLast(int value) {
        if ( isFull() ) {
            return false;
        }
        int r = rear;
        r = r == -1 ? 0 : (r + 1 + capacity) % capacity;
        front = front == -1 ? 0 : front;
        queue[rear = r] = value;
        size++;
        return true;
    }

    /**
     * Deletes an item from the front of Deque. Return true if the operation is successful.
     */
    public boolean deleteFront() {
        if ( size == 0 ) {
            return false;
        }
        if ( ++front == capacity ) {
            front = 0;
        }
        size--;
        return true;
    }

    /**
     * Deletes an item from the rear of Deque. Return true if the operation is successful.
     */
    public boolean deleteLast() {
        if ( size == 0 ) {
            return false;
        }
        if ( --rear < 0 ) {
            rear = capacity - 1;
        }
        size--;
        return true;
    }

    /**
     * Get the front item from the deque.
     */
    public int getFront() {
        /*if ( size == 0 ) {
            return -1;
        }*/
        return size > 0 ? queue[front] : -1;
    }

    /**
     * Get the last item from the deque.
     */
    public int getRear() {
        /*if ( size == 0 ) {
            return -1;
        }*/
        return size > 0 ? queue[rear] : -1;
    }

    /**
     * Checks whether the circular deque is empty or not.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks whether the circular deque is full or not.
     */
    public boolean isFull() {
        return size == capacity;
    }

}
