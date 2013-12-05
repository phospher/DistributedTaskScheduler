package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.mapred.*;
import org.apache.hadoop.io.*;
import java.io.*;
import java.util.*;

public class TaskInputSplit implements InputSplit {

	private List<Task> _taskList;

	public TaskInputSplit(List<Task> taskList) {
		this._taskList = taskList;
	}

	public long getLength() {
		return this._taskList.size();
	}

	public String[] getLocations() {
		return new String[] {};
	}

	public void readFields(DataInput in) {

	}

	public void write(DataOutput out) {

	}
}