package com.github.heaweavy.common.components.hadoop;

import com.github.heaweavy.common.components.hadoop.mapper.NginxLogMapper;
import com.github.heaweavy.common.components.hadoop.reducer.NginxLogReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

//        FileSystem fs = FileSystem.get(new URI("hdfs://ser5.dxlocal.net:9000"), conf);
/**
 * @author caimb
 * @date Created at 2019-03-31 19:57
 * @modefier
 */
public class NginxLogApp {
    public static void main(String[] args) throws Exception {
        Job job = Job.getInstance();
        job.setJarByClass( NginxLogApp.class );
        //设置作业名称，便于调试
        job.setJobName( "nginx log" );
        //添加输入路径，可以添加多个路径
        //输入路径不仅可以是具体文件，还可以是文件夹（目录，不会递归）
        Path inPath = new Path( args.length > 1 ? args[0] : "hdfs://ser5.dxlocal.net:9000/user/caimb/test/nginx-log" );
        Path outPath = new Path( args.length > 2 ? args[1] : "hdfs://ser5.dxlocal.net:9000/user/caimb/test/nginx-out" );
        FileInputFormat.addInputPath( job, inPath );
        //设置输出路径，输出路径只能一个，而且不能存在
        FileOutputFormat.setOutputPath( job, outPath );

        job.setMapperClass( NginxLogMapper.class );
        job.setReducerClass( NginxLogReducer.class );
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //获得配置信息
        Configuration conf = job.getConfiguration();
        //设置最大的切割尺寸
//        conf.setLong( FileInputFormat.SPLIT_MAXSIZE, 1024 * 1024 );//文件大于切割尺寸会被切割，小于切割尺寸则不切割。
        /*conf.set("mapreduce.framework.name", "yarn");*/
//        conf.set("hbase.zookeeper.quorum", "MASTER:2181");
        /*conf.set("fs.default.name", "hdfs://ser5.dxlocal.net:9000");
        conf.set("yarn.resourcemanager.resource-tracker.address", "ser5.dxlocal.net:8031");
        conf.set("yarn.resourcemanager.address", "ser5.dxlocal.net:8032");
        conf.set("yarn.resourcemanager.scheduler.address", "ser5.dxlocal.net:8030");
        conf.set("yarn.resourcemanager.admin.address", "ser5.dxlocal.net:8033");
        conf.set("yarn.application.classpath", "$HADOOP_LIB_HOME");
        conf.set("mapreduce.jobhistory.address", "ser5.dxlocal.net:10020");
        conf.set("mapreduce.jobhistory.webapp.address", "ser5.dxlocal.net:19888");
        conf.set("mapred.child.java.opts", "-Xmx1024m");*/

//        String pfk = args.length > 2 ? args[2] : "error";
//        conf.set("pathfilter.pattern", pfk);
//        FileInputFormat.setInputPathFilter(job, MyPathFilter.class);
        FileSystem fs = FileSystem.get(conf);
        if ( fs.exists( inPath ) ) {
            System.out.println("文件存在");
        } else System.out.println("文件不存在");
        if (fs.exists(outPath)) {
            fs.delete(outPath, true);
        }
        System.out.println("已删除输出文件");
        job.submit();
        System.out.println( job.getJobID().toString() );
        job.waitForCompletion( true );
    }
}
