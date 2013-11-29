package com.phospher.DistributedTaskScheduler.Configurations.XMLConfiguration;

import javax.xml.bind.annotation.*;
import java.util.*;
import com.phospher.DistributedTaskScheduler.Configurations.*;

@XmlRootElement(name = "Task")
public class XMLTask implements Task {
	private String _code;
	private String _name;
	private String _className;
	private List<Task> _tasks;
	private String _argsValues;
	private String[] _args;

	@XmlAttribute(name = "code")
	public String getCode() {
		return this._code;
	}

	public void setCode(String value) {
		this._code = value;
	}

	@XmlAttribute(name = "name")
	public String getName() {
		return this._name;
	}

	public void setName(String value) {
		this._name = value;
	}

	@XmlAttribute(name = "className")
	public String getClassName() {
		return this._className;
	}

	public void setClassName(String value) {
		this._className = value;
	}

	@XmlElementWrapper(name = "PrevTasks")
	@XmlElement(name = "Task", type = XMLTask.class)
	public List<Task> getTasks() {
		return this._tasks;
	}

	public void setTasks(List<Task> value) {
		this._tasks = value;
	}

	@XmlElement(name = "TaskArgs")
	public String getArgsValues() {
		return this._argsValues;
	}

	public void setArgsValues(String value) {
		this._argsValues = value;
	}

	@XmlTransient
	public String[] getArgs() {
		return this._args;
	}

	public void setArgs(String[] value) {
		this._args = value;
	}
}