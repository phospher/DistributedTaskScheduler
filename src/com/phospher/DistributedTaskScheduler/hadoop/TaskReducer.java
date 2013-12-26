package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Reducer;
import java.util.Iterator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.OutputCollector;

public class TaskReducer extends MapReduceBase implements Reducer<Text, TaskFeedBack, Text, TaskRunningResult> {

	public void reduce(Text key, Iterator<TaskFeedBack> values, OutputCollector<Text, TaskRunningResult> output, Reporter reporter) {

	}

}