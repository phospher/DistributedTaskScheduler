package com.phospher.DistributedTaskScheduler.configurations;

public interface TaskConfigurationPropertyGenerator {
	
	Task searchTask(TaskConfiguration taskConfiguration, String taskCode);

	String generateTaskCode(TaskConfiguration taskConfiguration, String taskCode);
}