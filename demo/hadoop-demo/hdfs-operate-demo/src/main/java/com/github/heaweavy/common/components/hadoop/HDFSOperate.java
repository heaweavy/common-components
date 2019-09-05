package com.github.heaweavy.common.components.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DistributedFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author caimb
 * @date Created at 2019-03-31 19:57
 * @modefier
 */
public class HDFSOperate {

    public static DistributedFileSystem dfs=new DistributedFileSystem();
    public static String nameNodeUri="hdfs://ser5.dxlocal.net:9000";

    public static void main(String[] args) throws Exception {
        HDFSOperate hdfs = new HDFSOperate();
        hdfs.initFileSystem();
        hdfs.testMkDir();
        hdfs.testDeleteDir();
        hdfs.testFileList();
//        hdfs.testUploadFullFile();
        hdfs.testUploadFullFile2();
        hdfs.testDownloadFile();
        hdfs.testUploadFile2();
        hdfs.testDownloadFile2();
//        FileSystem fs = FileSystem.get(new URI("hdfs://ser5.dxlocal.net:9000"), conf);
        /*Path inPath = new Path( args.length > 1 ? args[0] : "hdfs://ser5.dxlocal.net:9000/user/caimb/test/nginx-log" );
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        if ( fs.exists( inPath ) ) {
            System.out.println("文件存在");
        }*/
    }
    public void initFileSystem() throws Exception{
        System.out.println("初始化hadoop客户端");
        //设置hadoop的登录用户名
        System.setProperty("HADOOP_USER_NAME", "hdfs");
        //dfs=new DistributedFileSystem();
        dfs.initialize(new URI(nameNodeUri), new Configuration());
        System.out.println("客户端连接成功");
        Path workingDirectory = dfs.getWorkingDirectory();
        System.out.println("工作目录："+workingDirectory);
    }

    /**
     * 创建文件夹
     * @throws Exception
     */
    public void testMkDir() throws Exception{
        boolean res = dfs.mkdirs(new Path("/user/caimb/hdfstmpbbb"));
        System.out.println("目录创建结果："+(res?"创建成功":"创建失败"));
    }
    /**
     * 删除目录/文件
     * @throws Exception
     */
    //@Test
    public void testDeleteDir() throws Exception{
        dfs.delete(new Path("/user/caimb/hdfstmpbbb"), false);
    }

    /**
     * 获取指定目录下所有文件(忽略目录)
     * @throws Exception
     * @throws IllegalArgumentException
     */
    public void testFileList() throws Exception{
        RemoteIterator<LocatedFileStatus> listFiles = dfs.listFiles(new Path("/user/caimb/test/nginx-out/"), true);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = (LocatedFileStatus) listFiles.next();
            //权限
            FsPermission permission = fileStatus.getPermission();
            //拥有者
            String owner = fileStatus.getOwner();
            //组
            String group = fileStatus.getGroup();
            //文件大小byte
            long len = fileStatus.getLen();
            long modificationTime = fileStatus.getModificationTime();
            Path path = fileStatus.getPath();
            System.out.println("-------------------------------");
            System.out.println("permission:"+permission);
            System.out.println("owner:"+owner);
            System.out.println("group:"+group);
            System.out.println("len:"+len);
            System.out.println("modificationTime:"+sdf.format(new Date(modificationTime)));
            System.out.println("path:"+path);
        }
    }
    /**
     * 【完整】文件上传
     * 注意：文件上传在Window开发环境下，使用apache-common提供的<code>org.apache.commons.io.IOUtils.copy</code>可能存在问题
     */
    //@Test
    public void testUploadFullFile() throws Exception{
        FSDataOutputStream out = dfs.create(new Path("/user/caimb/hdfsTest/uploadFullFile.txt"), true);
        InputStream in = HDFSOperate.class.getClassLoader().getResourceAsStream("uploadFile.txt");
//        FileInputStream in = new FileInputStream(HDFSOperate.class.getResource("uploadFile.txt").getFile());
        String file = HDFSOperate.class.getClassLoader().getResource( "uploadFile.txt" ).getFile();
        System.out.println( file );
        org.apache.commons.io.IOUtils.copy(in, out);
        System.out.println("上传完毕");
    }

    /**
     * 【完整】文件上传
     */
    //@Test
    public void testUploadFullFile2() throws Exception{
        long start = System.currentTimeMillis();
        dfs.copyFromLocalFile( new Path( "/data/middle-data/tmp/hadoop/uploadFile.txt" ), new Path( "/user/caimb/hdfsTest/uploadFullFile.txt" ) );
        System.out.println( "上传完毕 : " + (System.currentTimeMillis() - start) );
    }

    /**
     * 【分段|部分】文件上传
     * 注意：文件上传在Window开发环境下，使用apache-common提供的<code>org.apache.commons.io.IOUtils.copy</code>可能存在问题
     */
    public void testUploadFile2() throws Exception{
        FSDataOutputStream out = dfs.create(new Path("/user/caimb/hdfsTest/uploadFile2.txt"), true);
        InputStream in = HDFSOperate.class.getClassLoader().getResourceAsStream("uploadFile.txt");
        org.apache.commons.io.IOUtils.copyLarge(in, out, 6, 12);
        System.out.println("上传完毕");
    }
    /**
     * 【完整】下载文件
     * 注意：windows开发平台下，使用如下API
     */
    public void testDownloadFile() throws Exception{
        //使用Java API进行I/O,设置useRawLocalFileSystem=true
        dfs.copyToLocalFile(false,new Path("/user/caimb/hdfsTest/uploadFullFile.txt"), new Path("/data/middle-data/tmp/hadoop/demo/download_FullFile.txt"),true);
        System.out.println("下载完成");
    }

    /**
     * 【部分】下载文件
     */
    public void testDownloadFile2() throws Exception{
        //使用Java API进行I/O,设置useRawLocalFileSystem=true
        FSDataInputStream src = dfs.open(new Path("/user/caimb/hdfsTest/uploadFullFile.txt"));
        FileOutputStream des = new FileOutputStream(new File("/data/middle-data/tmp/hadoop/demo","download_fragment.txt"));
        src.seek(6);
        org.apache.commons.io.IOUtils.copy(src, des);
        System.out.println("下载完成");
    }

}