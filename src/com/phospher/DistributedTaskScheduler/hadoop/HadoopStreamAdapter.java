package com.phospher.DistributedTaskScheduler.hadoop;

import java.io.*;
import org.apache.hadoop.conf.*;

public interface HadoopStreamAdapter {
	InputStream getInputStream(Configuration conf) throws Exception;
}