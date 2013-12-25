package com.phospher.DistributedTaskScheduler;

import com.phospher.DistributedTaskScheduler.hadoop.TaskResult;

public interface TaskProcess {
	TaskResult run(String[] args) throws Exception;
}