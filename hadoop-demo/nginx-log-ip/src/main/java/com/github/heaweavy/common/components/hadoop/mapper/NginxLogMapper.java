package com.github.heaweavy.common.components.hadoop.mapper;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author caimb
 * @date Created at 2019-03-31 19:34
 * @modefier
 */
public class NginxLogMapper extends Mapper<LongWritable, Text, Text, Text> {
    private static final int MISSING = 9999;
    private InetAddress addr;
    private String ip;
    private String hostname;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private long startTime;
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        try {
            Date d = getDateByValue( line );// DATE
            SimpleDateFormat f = new SimpleDateFormat( "yyyy-MM-dd" );
            Text k = new Text( f.format( d ) );
            // 以日期分组
            context.write( k, new Text( line ) );
        } catch (Exception e) {
            System.out.println("MAPPER ++++++++++++++++++++++++++"+e.getMessage());
        }
    }

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

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        long duration = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println( hostname + "-" + ip + ":" + sdf.format( new Date() ) + ":mapper.clearup : " + duration);
    }

    private Date getDateByValue(String vs) throws ParseException {
        String date = vs.substring(vs.indexOf("["), vs.indexOf("]") + 1);
        SimpleDateFormat format = new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ss Z]", Locale.US);
        Date d = format.parse(date);
        return d;
    }
}
