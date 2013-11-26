package com.phospher.DistributedTaskScheduler.Configurations;

import java.util.*;

public interface TaskConfiguration {
	List<Task> getTasks();
	void setTasks(List<Task> value);
}