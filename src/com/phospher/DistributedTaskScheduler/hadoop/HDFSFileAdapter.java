package com.phospher.DistributedTaskScheduler.hadoop;

import java.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;

public class HDFSFileAdapter implements HadoopStreamAdapter {

	public InputStream getInputStream(Configuration conf, String filePathPropertyName) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(conf.getRaw(filePathPropertyName));
		return fs.open(path);
	}

	public void append(Configuration conf, String filePathPropertyName, String text) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(conf.getRaw(filePathPropertyName));
		FSDataOutputStream fsdout = fs.append(path);
		OutputStreamWriter outWriter = new OutputStreamWriter(fsdout, "UTF-8");
		outWriter.append(text);
		outWriter.flush();
	}

	public void createOrReplace(Configuration conf, String filePathPropertyName) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(conf.getRaw(filePathPropertyName));
		fs.create(path, true);
	}

	public boolean exists(Configuration conf, String filePathPropertyName) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(conf.getRaw(filePathPropertyName));
		return fs.exists(path);
	}

}