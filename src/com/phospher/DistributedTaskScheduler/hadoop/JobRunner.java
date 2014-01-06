package com.phospher.DistributedTaskScheduler.hadoop;

import java.io.*;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RunningJob;

public interface JobRunner {

	RunningJob runJob(JobConf jobConf) throws IOException;

}