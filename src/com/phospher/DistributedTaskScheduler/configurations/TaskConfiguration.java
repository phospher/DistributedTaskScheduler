package com.phospher.DistributedTaskScheduler.configurations;

import java.util.*;

public interface TaskConfiguration {
	List<Task> getTasks();
	void setTasks(List<Task> value);
}