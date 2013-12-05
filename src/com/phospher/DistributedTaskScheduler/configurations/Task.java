package com.phospher.DistributedTaskScheduler.configurations;

import java.util.*;
import java.io.*;
import org.apache.hadoop.io.*;

public class Task implements Writable {
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
		UTF8.writeString(out, this._code);
		UTF8.writeString(out, this._name);
		UTF8.writeString(out, this._className);
		ObjectWritable.writeObject(out, this._tasks, Task[].class, null);
		ObjectWritable.writeObject(out, this._args, String[].class, null);
	}

	public void readFields(DataInput in) throws IOException {
		this._code = UTF8.readString(in);
		this._name = UTF8.readString(in);
		this._className = UTF8.readString(in);
	    this._tasks = (Task[])ObjectWritable.readObject(in, null);
	    this._args = (String[])ObjectWritable.readObject(in, null);
	}
}