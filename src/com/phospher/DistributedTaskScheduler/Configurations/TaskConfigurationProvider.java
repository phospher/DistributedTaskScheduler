package com.phospher.DistributedTaskScheduler.Configurations;

public interface TaskConfigurationProvider {
	TaskConfiguration getConfiguration() throws Exception;
}