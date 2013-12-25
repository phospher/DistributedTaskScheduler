package com.phospher.DistributedTaskScheduler.hadoop;

import java.io.*;
import org.apache.hadoop.conf.*;

public interface HadoopStreamAdapter {
	InputStream getInputStream(Configuration conf, String filePathPropertyName) throws IOException;

	void append(Configuration conf, String filePathPropertyName, String text) throws IOException;

	void createOrReplace(Configuration conf, String filePathPropertyName) throws IOException;
}