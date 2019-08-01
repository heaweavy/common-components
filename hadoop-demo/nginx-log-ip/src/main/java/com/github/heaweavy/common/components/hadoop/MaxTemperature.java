package com.github.heaweavy.common.components.hadoop;

import com.github.heaweavy.common.components.hadoop.mapper.MaxTemperatureMapper;
import com.github.heaweavy.common.components.hadoop.reducer.MaxTemperatureReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * 第一种方式：
 * >export HADOOP_CLASSPATH=hadoop-examples.jar
 * >hadoop MaxTemperature input/ncdc/sample.txt output
 * 第二种：
 * hadoop jar HadoopDemo.jar /user/ncdc_data /user/out
 * app入口
 * @author caimb
 * @date Created at 2019-03-31 19:57
 * @modefier
 */
public class MaxTemperature {
    public static void main(String[] args) throws Exception {
        Job job = new Job();
        //获得配置信息
        Configuration conf = job.getConfiguration();
        //设置最大的切割尺寸
        conf.setLong( FileInputFormat.SPLIT_MAXSIZE, 1024 * 15 );
        job.setJarByClass( MaxTemperature.class );
        //设置作业名称，便于调试
        job.setJobName( "Max temperature" );
        //添加输入路径，可以添加多个路径
        //输入路径不仅可以是具体文件，还可以是文件夹（目录，不会递归）
        FileInputFormat.addInputPath( job, new Path( args[0] ) );
        //设置输出路径，输出路径只能一个，而且不能存在
        FileOutputFormat.setOutputPath( job, new Path( args[1] ) );
        job.setMapperClass( MaxTemperatureMapper.class );
        job.setReducerClass( MaxTemperatureReducer.class );
        job.setOutputKeyClass( Text.class );
        job.setOutputValueClass( IntWritable.class );
        job.waitForCompletion( true );
    }
}
