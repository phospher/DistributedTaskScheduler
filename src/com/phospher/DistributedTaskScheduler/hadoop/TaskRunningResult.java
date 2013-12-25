package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.io.Writable;
import java.io.*;
import com.phospher.DistributedTaskScheduler.util.ObjectSerilizationUtil;

public class TaskRunningResult implements Writable, Serializable {

	private TaskResult _result;
	private String _message;
	private Exception _exception;

	public TaskResult getResult() {
		return this._result;
	}

	public void setResult(TaskResult value) {
		this._result = value;
	}

	public String getMessage() {
		return this._message;
	}

	public void setMessage(String value) {
		this._message = value;
	}

	public Exception getException() {
		return this._exception;
	}

	public void setException(Exception value) {
		this._exception = value;
	}

	public void write(DataOutput out) throws IOException {
		ObjectSerilizationUtil.writeObject(out, this);
	}

	public void readFields(DataInput in) throws IOException {
		TaskRunningResult obj = (TaskRunningResult)ObjectSerilizationUtil.readObject(in);
		this._result = obj.getResult();
		this._message = obj.getMessage();
		this._exception = obj.getException();
	} 

}