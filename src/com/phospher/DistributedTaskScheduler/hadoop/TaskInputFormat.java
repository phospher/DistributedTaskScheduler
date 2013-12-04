package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.mapred.*;
import org.apache.hadoop.io.*;

public class TaskInputFormat implements InputFormat<Text, ObjectWritable> {

	public RecordReader<Text, ObjectWritable> getRecordReader(InputSplit split, JobConf conf, Reporter reporter) {
		return null;
	}

	public InputSplit[] getSplits(JobConf conf, int numSplits) {
		return null;
	}

}