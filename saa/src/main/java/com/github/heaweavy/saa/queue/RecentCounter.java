package com.github.heaweavy.saa.queue;

/**
 * leetcode
 * 933 最近的请求数
 * 使用环形数组队列
 * @author caimb
 * @date Created at 2019-07-09 14:17
 * @modefier
 */
public class RecentCounter {
    private static final int CAPACITY = 3001;
    private static final int PING_INTERVAL = 3000;
    private Queue queue;

    public RecentCounter() {
        queue = new Queue();
    }

    public int ping(int t) {
        return queue.offer( t );
    }
    private static class Queue{
        int[] queue = new int[CAPACITY];
        int front = 0;
        int rear = 0;

        public int offer(int ping) {
            int f = front,r;
            queue[rear++ % CAPACITY] = ping;
            r = rear;
            if ( r > CAPACITY && ((r - 1) % CAPACITY) == f ) {
                f++;
                if ( f >= CAPACITY ) {
                    front = f -= CAPACITY;
                    rear = r -= CAPACITY;
                } else {
                    front = f;
                }
            }
            if ( ping - queue[f] <= PING_INTERVAL ) {
                return r - f;
            }
            for ( f++; f != r; f++ ) {
                if ( ping - queue[f % CAPACITY] <= PING_INTERVAL ) {
                    break;
                }
            }
            if ( f >= CAPACITY ) {
                f -= CAPACITY;
                r -= CAPACITY;
            }
            front = f;
            rear = r;
            return r - f;
        }
    }
}
