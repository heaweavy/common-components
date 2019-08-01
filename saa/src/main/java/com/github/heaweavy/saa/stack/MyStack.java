package com.github.heaweavy.saa.stack;

import java.util.ArrayDeque;

/**
 * leetcode
 * 225. 用队列实现栈
 *
 * @author caimb
 * @date Created at 2019-07-11 11:09
 * @modefier
 */
public class MyStack {
    private ArrayDeque<Integer> deque;
    /** Initialize your data structure here. */
    public MyStack() {
        deque = new ArrayDeque<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        deque.addFirst( x );
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        return deque.removeFirst();
    }

    /** Get the top element. */
    public int top() {
        return deque.peekFirst();
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return deque.isEmpty();
    }

    public static void main(String[] args) {
        MyStack stack = new MyStack();
        stack.push( 1 );
        stack.push( 2 );
        System.out.println( stack.pop() );
        System.out.println( stack.top() );
        System.out.println( stack.empty() );
    }
}
