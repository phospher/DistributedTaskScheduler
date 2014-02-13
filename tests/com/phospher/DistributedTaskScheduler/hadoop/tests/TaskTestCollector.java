package com.phospher.DistributedTaskScheduler.hadoop.tests;

import com.phospher.DistributedTaskScheduler.hadoop.*;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.io.Text;
import java.util.HashMap;
import java.io.IOException;

public class TaskTestCollector implements OutputCollector<Text, TaskRunningResult> {

	private HashMap<Text, TaskRunningResult> _data;

	public TaskTestCollector() throws IOException {
		this._data = new HashMap<Text, TaskRunningResult>();
	}

	public TaskRunningResult get(Text key) {
		return this._data.get(key);
	}

	public void collect(Text key, TaskRunningResult value) throws IOException {
		this._data.put(key, value);
	}

}