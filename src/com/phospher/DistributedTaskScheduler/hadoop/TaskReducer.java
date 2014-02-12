package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.mapred.Reducer;
import java.util.Iterator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.OutputCollector;
import java.io.IOException;

public class TaskReducer extends TaskMapReduceBase implements Reducer<Text, TaskRunningResult, Text, TaskRunningResult> {

	public void reduce(Text key, Iterator<TaskRunningResult> values, OutputCollector<Text, TaskRunningResult> output, Reporter reporter) throws IOException {
		TaskResult taskResult = TaskResult.SUCCESS;
		while(values.hasNext()) {
			TaskRunningResult result = values.next();
			if(result.getResult() == TaskResult.WARNING) {
				taskResult = TaskResult.WARNING;
			} else if (result.getResult() == TaskResult.FAILURE) {
				taskResult = TaskResult.FAILURE;
				break;
			}
		}
		TaskRunningResult runningResult = new TaskRunningResult();
		runningResult.setResult(taskResult);
		output.collect(key, runningResult);
	}

}