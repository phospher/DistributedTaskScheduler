package com.phospher.DistributedTaskScheduler.configurations;

import org.apache.hadoop.conf.*;

public interface TaskConfigurationPropertyGenerator {
	
	Task searchTask(TaskConfiguration taskConfiguration, String taskCode);

	String generateTaskCode(Configuration configuration, String taskCode);
}