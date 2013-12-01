package com.phospher.DistributedTaskScheduler.Configurations;

import java.util.*;
import java.io.*;

public class Task implements Serializable {
	private String _code;
	private String _name;
	private String _className;
	private List<Task> _tasks;
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

	public List<Task> getTasks() {
		return this._tasks;
	}

	public void setTasks(List<Task> value) {
		this._tasks = value;
	}

	public String[] getArgs() {
		return this._args;
	}

	public void setArgs(String[] value) {
		this._args = value;
	}
}