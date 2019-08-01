package com.github.heaweavy.saa.queue;

/**
 * leetcode
 * 933 最近的请求数
 * 使用链式队列
 * @author caimb
 * @date Created at 2019-07-09 14:17
 * @modefier
 */
public class RecentCounterLink {
    private static final int PING_INTERVAL = 3000;
    private Queue queue;

    public RecentCounterLink() {
        queue = new Queue();
    }

    public int ping(int t) {
        return queue.offer( t );
    }
    private static class Queue{
        int size = 0;
        Node front = new Node( -1, null );
        Node rear = front;

        public int offer(int ping) {
            rear.next = new Node( ping, null );
            rear = rear.next;
            size++;
            final Node f = front;
            while ( size != 0 ) {
                if ( ping - f.next.data <= PING_INTERVAL ) {
                    break;
                }
                f.next = f.next.next;
                size--;
            }
            if ( size == 0 ) {
                rear = f;
            }
            return size;
        }

        private static class Node {
            int data;
            Node next;

            public Node(int data, Node next) {
                this.data = data;
                this.next = next;
            }
        }
    }
}
