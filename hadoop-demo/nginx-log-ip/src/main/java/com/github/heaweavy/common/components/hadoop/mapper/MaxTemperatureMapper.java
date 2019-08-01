package com.github.heaweavy.common.components.hadoop.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author caimb
 * @date Created at 2019-03-31 19:34
 * @modefier
 */
public class MaxTemperatureMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private static final int MISSING = 9999;
    private InetAddress addr;
    private String ip;
    private String hostname;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private long startTime;
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        //提取年份数据
        String year = line.substring( 15, 19 );
        //气温变化
        int airTemperature;
        if ( line.charAt( 87 ) == '+' ) {
            // parseInt doesn't like leading plus signs
            airTemperature = Integer.parseInt( line.substring( 88, 92 ) );
        } else{
            airTemperature = Integer.parseInt( line.substring( 87, 92 ) );
        }
        //质量
        String quality = line.substring( 92, 93 );
        //将有效数据写入到map的context中，注意类型务必要和泛型声明相一致
        if ( airTemperature != MISSING && quality.matches( "[01459]" ) ) {
            context.write( new Text( year ), new IntWritable( airTemperature ) );
        }
    }

    /**
     * map执行完成后清理过程，只调用一次
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //获得输入切割数据
        InputSplit split = context.getInputSplit();
        long length = split.getLength();
        addr = InetAddress.getLocalHost();
        ip = addr.getHostAddress();
        hostname = addr.getHostName();
        startTime = System.currentTimeMillis();
        System.out.println( "split - " + length + "-" + hostname + "-" + ip + ":" + sdf.format( new Date() ) + ":mapper.setup" );
    }

    /**
     * 在map task执行之前调用，只调用一次
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        long duration = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println( hostname + "-" + ip + ":" + sdf.format( new Date() ) + ":mapper.clearup : " + duration);
    }
}
