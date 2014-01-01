package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.conf.Configuration;

public class TaskMapReduceBase extends MapReduceBase {

	private JobConf _job;

	protected JobConf getJob() {
		if(this._job == null) {
			this._job = new JobConf();
		}
		return this._job;
	}

	public void configure(JobConf job) {
		this._job = job;
	}

}