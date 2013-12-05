package com.phospher.DistributedTaskScheduler.configurations.xmlconfigurations;

import javax.xml.bind.annotation.*;
import java.util.*;
import com.phospher.DistributedTaskScheduler.configurations.*;

@XmlRootElement(name = "Task")
public class XMLTask extends Task {
	
	private String _argsValues;

	@XmlAttribute(name = "code")
	public String getCode() {
		return super.getCode();
	}

	public void setCode(String value) {
		super.setCode(value);
	}

	@XmlAttribute(name = "name")
	public String getName() {
		return super.getName();
	}

	public void setName(String value) {
		super.setName(value);
	}

	@XmlAttribute(name = "className")
	public String getClassName() {
		return super.getClassName();
	}

	public void setClassName(String value) {
		super.setClassName(value);
	}

	@XmlElementWrapper(name = "PrevTasks")
	@XmlElement(name = "Task", type = XMLTask.class)
	public Task[] getTasks() {
		return super.getTasks();
	}

	public void setTasks(Task[] value) {
		super.setTasks(value);
	}

	@XmlElement(name = "TaskArgs")
	public String getArgsValues() {
		return this._argsValues;
	}

	public void setArgsValues(String value) {
		this._argsValues = value;
		LinkedList<String> result = new LinkedList();
		for(String item : this._argsValues.split(" ")) {
			if(item != null && !item.trim().isEmpty()) {
				result.add(item);
			}
		}

		String[] resultArray = new String[result.size()];
		result.toArray(resultArray);
		this.setArgs(resultArray);
	}

	@XmlTransient
	public String[] getArgs() {
		return super.getArgs();
	}
}