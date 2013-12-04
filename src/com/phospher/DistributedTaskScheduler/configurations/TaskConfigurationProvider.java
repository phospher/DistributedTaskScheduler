package com.phospher.DistributedTaskScheduler.configurations;

public interface TaskConfigurationProvider {
	TaskConfiguration getConfiguration() throws Exception;
}