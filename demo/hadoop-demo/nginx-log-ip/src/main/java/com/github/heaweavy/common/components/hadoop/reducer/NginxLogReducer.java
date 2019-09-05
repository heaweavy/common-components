package com.github.heaweavy.common.components.hadoop.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author caimb
 * @date Created at 2019-03-31 19:34
 * @modefier
 */
public class NginxLogReducer extends Reducer<Text, Text, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Set<String> set = new HashSet<>();
        for(Text value : values){
            String vs = value.toString();
            String[] arr = vs.split("- -");
            String ip = arr[0].trim();// IP
            set.add( ip );
        }
        System.out.println( "RRRRRRR <><> " + set );
        context.write(key, new IntWritable(set.size()));
    }
}
