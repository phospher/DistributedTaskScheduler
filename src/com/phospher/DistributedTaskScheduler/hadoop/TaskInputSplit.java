package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.mapred.InputSplit;
import com.phospher.DistributedTaskScheduler.configurations.*;
import org.apache.hadoop.io.*;
import java.io.*;
import java.util.*;
import com.phospher.DistributedTaskScheduler.util.*;

public class TaskInputSplit implements InputSplit, Serializable {

	private Task[] _tasks;

	public TaskInputSplit(Task[] tasks) {
		this._tasks = tasks;
	}

	public long getLength() {
		return this._tasks.length;
	}

	public String[] getLocations() {
		return new String[] {};
	}

	public Task[] getTasks() {
		return this._tasks;
	}

	public void readFields(DataInput in) throws IOException {
		TaskInputSplit obj = (TaskInputSplit)ObjectSerilizationUtil.readObject(in);
		this._tasks = obj.getTasks();
	}

	public void write(DataOutput out) throws IOException {
		ObjectSerilizationUtil.writeObject(out, this);
	}
}