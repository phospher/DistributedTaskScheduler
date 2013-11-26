package com.phospher.DistributedTaskScheduler.Configurations;

import java.util.*;

public interface Task {
	String getCode();
	void setCode(String value);

	String getName();
	void setName(String value);

	String getOperationClass();
	void setOperationClass(String value);

	List<Task> getTasks();
	void setTasks(List<Task> value);
}