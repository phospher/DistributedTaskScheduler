package com.phospher.DistributedTaskScheduler.configurations;

import java.util.*;
import java.io.*;
import com.phospher.DistributedTaskScheduler.util.*;
import org.apache.hadoop.io.*;

public class Task implements Writable, Serializable {
	private String _code;
	private String _name;
	private String _className;
	private Task[] _tasks;
	private String[] _args;

	public String getCode() {
		return this._code;
	}

	public void setCode(String value) {
		this._code = value;
	}

	public String getName() {
		return this._name;
	}

	public void setName(String value) {
		this._name = value;
	}

	public String getClassName() {
		return this._className;
	}

	public void setClassName(String value) {
		this._className = value;
	}

	public Task[] getTasks() {
		return this._tasks;
	}

	public void setTasks(Task[] value) {
		this._tasks = value;
	}

	public String[] getArgs() {
		return this._args;
	}

	public void setArgs(String[] value) {
		this._args = value;
	}

	public void write(DataOutput out) throws IOException {
		ObjectSerilizationUtil.writeObject(out, this);
	}

	public void readFields(DataInput in) throws IOException {
		Task obj = (Task)ObjectSerilizationUtil.readObject(in);
		this._code = obj.getCode();
		this._name = obj.getName();
		this._className = obj.getClassName();
		this._tasks = obj.getTasks();
		this._args = obj.getArgs();
	}
}