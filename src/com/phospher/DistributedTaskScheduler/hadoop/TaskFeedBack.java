package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.io.Writable;
import java.io.*;
import com.phospher.DistributedTaskScheduler.util.ObjectSerilizationUtil;

public class TaskFeedBack implements Writable, Serializable {

	private TaskRunningResult _result;
	private String _taskCode;

	public TaskRunningResult getResult() {
		return this._result;
	}

	public void setResult(TaskRunningResult value) {
		this._result = value;
	}

	public String getTaskCode() {
		return this._taskCode;
	}

	public void setTaskCode(String value) {
		this._taskCode = value;
	}

	public void write(DataOutput out) throws IOException {
		ObjectSerilizationUtil.writeObject(out, this);
	}

	public void readFields(DataInput in) throws IOException {
		TaskFeedBack obj = (TaskFeedBack)ObjectSerilizationUtil.readObject(in);
		this._result = obj.getResult();
		this._taskCode = obj.getTaskCode();
	} 

}