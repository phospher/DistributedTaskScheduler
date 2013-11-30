package com.phospher.DistributedTaskScheduler.Configurations.XMLConfiguration;

import com.phospher.DistributedTaskScheduler.Configurations.*;
import java.util.*;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "TaskConfiguration")
public class XMLTaskConfiguration implements TaskConfiguration {
	private List<Task> _tasks;

	@XmlElementWrapper(name = "Tasks")
	@XmlElement(name = "Task", type = XMLTask.class)
	public List<Task> getTasks() {
		return this._tasks;
	}

	public void setTasks(List<Task> value) {
		this._tasks = value;
	}
}