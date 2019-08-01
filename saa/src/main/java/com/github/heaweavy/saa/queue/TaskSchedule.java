package com.github.heaweavy.saa.queue;

import java.util.Arrays;

/**
 * leetcode
 * 621. 任务调度器
 * @author caimb
 * @date Created at 2019-07-10 14:42
 * @modefier
 */
public class TaskSchedule {
    private static final int[] TASK_TYPES = new int[26];
    static {
        for ( int i=0;i<26;i++ ) {
            TASK_TYPES[i] = 1 << i;
        }
    }
    static int[] TNP = new int[26];//任务在tns中的位置。

    public int leastInterval2(char[] tasks, int n) {

        int[] taskCounts = new int[26];
        for ( int i = 0; i < tasks.length; i++ ) {
            taskCounts[tasks[i] - 'A']++;
        }
        Arrays.parallelSort( taskCounts );
        int maxCount = 1;
        for ( int i = 25; i > 0; i-- ) {
            if ( taskCounts[i] == taskCounts[i - 1] ) {
                maxCount++;
            } else {
                break;
            }
        }
        return Math.max( tasks.length, (n + 1) * (taskCounts[25] - 1) + maxCount );
    }

    public int leastInterval(char[] tasks, int n) {
        int[] TNS = new int[26];//各种任务数
        int TNT = 0;//任务种类数
        int taskType = 0;//任务种类用其中26位来记录是否存现过这个任务
        int t;
        for ( char task : tasks ) {
            TNS[t =task - 'A']++;
            if ( (taskType & TASK_TYPES[t]) == 0 ) {
                TNP[TNT++] = t;
            }
            taskType |= TASK_TYPES[t];
        }
        int lastMaxTT = 0, maxTT = 0, maxTTP = 0, cur;//最多的一种任务的数量，与最多的任务出现相同次数的任务种类数
        for ( int i = 0; i < TNT; i++ ) {
            cur = TNS[TNP[i]];
            if ( cur > maxTT ) {
                maxTT = cur;
                if ( lastMaxTT < maxTT ) {
                    lastMaxTT = maxTT;
                    maxTTP = 1;
                }
            } else if ( cur == maxTT ) {
                maxTTP++;
            }
        }
        return ((cur = (maxTT - 1) * (n + 1) + maxTTP) < tasks.length) ? tasks.length : cur;
    }

}
