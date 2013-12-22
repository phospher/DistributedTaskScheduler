package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.mapred.RecordReader;
import com.phospher.DistributedTaskScheduler.configurations.Task;
import java.io.IOException;
import org.apache.hadoop.io.*;

public class TaskRecordReader implements RecordReader<Text, Task> {

	private Task[] _tasks;
	private int _currentIndex;

	public TaskRecordReader(Task[] task) {
		this._tasks = task;
		this._currentIndex = 0;
	}

	public  Text createKey() {
		return new Text();
	}
	
	public Task createValue() {
		return new Task();
	}

	public long getPos() throws IOException {
		return (long)this._currentIndex;
	}

	public float getProgress() throws IOException {
		return (float)this._currentIndex / this._tasks.length;
	}

	public boolean next(Text key, Task value) throws IOException {
		if(this._currentIndex >= this._tasks.length) {
			return false;
		}

		key.set(this._tasks[this._currentIndex].getCode());
		value.setCode(this._tasks[this._currentIndex].getCode());
		value.setName(this._tasks[this._currentIndex].getName());
		value.setClassName(this._tasks[this._currentIndex].getClassName());
		value.setTasks(this._tasks[this._currentIndex].getTasks());
		value.setArgs(this._tasks[this._currentIndex].getArgs());
		this._currentIndex++;
		return true;
	}

	public void close() {
		this._currentIndex = this._tasks.length;
	}
}