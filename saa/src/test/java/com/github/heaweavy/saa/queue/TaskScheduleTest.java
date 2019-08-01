package com.github.heaweavy.saa.queue;

import org.junit.Test;

/**
 * @author caimb
 * @date Created at 2019-07-10 15:37
 * @modefier
 */
public class TaskScheduleTest {
    @Test
    public void test() {
        TaskSchedule taskSchedule = new TaskSchedule();
//        char[] task = {'A', 'A', 'A', 'B', 'B', 'B', 'C', 'C', 'D', 'D'};
        char[] task = {'A', 'A', 'A', 'B', 'B', 'B'};
//        System.out.println( taskSchedule.leastInterval( task, 3 ) );
        System.out.println( taskSchedule.leastInterval2( task, 0 ) );
    }
}
