package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.mapred.*;
import org.apache.hadoop.io.*;
import com.phospher.DistributedTaskScheduler.configurations.*;
import com.phospher.DistributedTaskScheduler.ioc.*;
import java.io.*;

public class TaskInputFormat implements InputFormat<Text, com.phospher.DistributedTaskScheduler.configurations.Task> {

	public final static String OBJECT_PROVIDER_PROPERTY = "distributedtaskconfig.objectprovider.classname";
	public final static String CURRENT_RULE_PROPERTY = "distributedtaskconnfig.currentrule.code";

	public RecordReader<Text, com.phospher.DistributedTaskScheduler.configurations.Task> getRecordReader(InputSplit split, JobConf conf, Reporter reporter) throws IOException {
		return null;
	}

	public InputSplit[] getSplits(JobConf conf, int numSplits) throws IOException {
		try {
			ObjectProvider objectProvider = (ObjectProvider)Class.forName(conf.get(OBJECT_PROVIDER_PROPERTY)).newInstance();
			TaskConfigurationProvider configurationProvider = (TaskConfigurationProvider)objectProvider.getInstance(TaskConfigurationProvider.class);
			TaskConfiguration taskConfiguration = configurationProvider.getConfiguration(conf);
			return null;
		} catch(Exception ex) {
			throw new IOException(ex);
		}
	}

}