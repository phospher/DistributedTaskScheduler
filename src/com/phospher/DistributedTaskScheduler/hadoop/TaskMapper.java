package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.io.Text;
import com.phospher.DistributedTaskScheduler.configurations.Task;

public class TaskMapper extends MapReduceBase implements Mapper<Text, Task, Text, TaskRunningResult> {

	public void map(Text key, Task value, OutputCollector<Text, TaskRunningResult> output, Reporter reporter) {

	}

}