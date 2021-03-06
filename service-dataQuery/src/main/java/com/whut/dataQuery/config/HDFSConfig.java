package com.whut.dataQuery.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;

/**
 * Created by 杨赟 on 2018-07-20.
 */
public class HDFSConfig {

    private static FileSystem fileSystem = null;

    static {
        Configuration conf = new Configuration();
        try {
            fileSystem =  FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileSystem getFileSystem()
    {
       return fileSystem;
    }

    public static void close()
    {
        try {
            fileSystem.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
