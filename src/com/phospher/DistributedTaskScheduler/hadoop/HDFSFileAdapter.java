package com.phospher.DistributedTaskScheduler.hadoop;

import java.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;

public class HDFSFileAdapter implements HadoopStreamAdapter {

	private final static String FILE_PATH_PROPERTY = "distributedtaskconfig.filepath";

	public InputStream getInputStream(Configuration conf) throws Exception {
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(conf.getRaw(FILE_PATH_PROPERTY));
		return fs.open(path);
	}

}