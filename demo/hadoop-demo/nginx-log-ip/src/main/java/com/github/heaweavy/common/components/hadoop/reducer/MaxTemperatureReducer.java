package com.github.heaweavy.common.components.hadoop.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author caimb
 * @date Created at 2019-03-31 19:34
 * @modefier
 */
public class MaxTemperatureReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int maxValue = Integer.MIN_VALUE;
        for ( IntWritable value : values ) {
            maxValue = Math.max( maxValue, value.get() );
        }
        //将reduce的输出写入到context中
        context.write( key, new IntWritable( maxValue ) );
    }
}
