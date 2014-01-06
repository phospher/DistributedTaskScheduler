package com.phospher.DistributedTaskScheduler.hadoop;

import java.io.*;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.RunningJob;

public class HadoopJobRunner implements JobRunner {

	public RunningJob runJob(JobConf jobConf) throws IOException {
		RunningJob runningJob = JobClient.runJob(jobConf);
		runningJob.waitForCompletion();
		return runningJob;
	}

}