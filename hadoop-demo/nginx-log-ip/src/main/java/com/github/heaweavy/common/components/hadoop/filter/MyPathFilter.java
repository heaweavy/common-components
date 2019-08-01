package com.github.heaweavy.common.components.hadoop.filter;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

import java.io.IOException;

/**
 * @author caimb
 * @date Created at 2019-04-03 16:31
 * @modefier
 */
public class MyPathFilter implements PathFilter, Configurable {
    Configuration conf = null;
    FileSystem fs = null;

    @Override
    public Configuration getConf() {
        return this.conf;
    }

    @Override
    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public boolean accept(Path path) {
        try {
            fs = FileSystem.get(conf);
            FileStatus fileStatus = fs.getFileStatus(path);
            if (!fileStatus.isDirectory()) {
                String fileName = path.getName();
                if ( fileName.contains( "access" ) && !fileName.contains( conf.get( "pathfilter.pattern" ) ) ) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("MyPathFilter ++++++++++++++++++++++++++");
            e.printStackTrace();
        }
        return false;
    }
}
