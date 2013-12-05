package com.phospher.DistributedTaskScheduler.configurations;

import org.apache.hadoop.conf.*;

public interface TaskConfigurationProvider {
	TaskConfiguration getConfiguration(Configuration conf) throws Exception;
}