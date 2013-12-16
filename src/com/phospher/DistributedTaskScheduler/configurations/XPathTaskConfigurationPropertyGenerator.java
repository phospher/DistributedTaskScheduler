package com.phospher.DistributedTaskScheduler.configurations;

import org.apache.hadoop.conf.*;

public class XPathTaskConfigurationPropertyGenerator implements TaskConfigurationPropertyGenerator {

	private final static String SPLIT_TOKEN = "/";

	public String generateTaskCode(Configuration configuration, String taskCode) {
		String parentCode = configuration.get(ConfigurationPropertyName.CURRENT_RULE_PROPERTY.getPropertyName());
		return parentCode == null || parentCode.isEmpty() ? taskCode : parentCode + SPLIT_TOKEN + taskCode;
	}

	public Task searchTask(TaskConfiguration taskConfiguration, String taskCode) {
		String[] taskCodes = taskCode.split(SPLIT_TOKEN);
		Task currentTask = null;

		if(taskCodes.length > 0 ) {
			//start searching in taskConfiguraiton
			currentTask = this.searchTaskInTaskArray(taskConfiguration.getTasks().toArray(new Task[taskConfiguration.getTasks().size()]), taskCodes[0]);
			
			//if root task do not exist, no need to search any more
			//and if any task do not exit, stop searching immediately
			if(currentTask != null) {
				for(int i = 1; i < taskCodes.length; i++) {
					currentTask = this.searchTaskInTaskArray(currentTask.getTasks(), taskCodes[i]);
					if(currentTask == null) {
						break;
					}
				}
			}
		}
		return currentTask;
	}

	private Task searchTaskInTaskArray(Task[] tasks, String taskCode) {
		if(taskCode != null && !taskCode.trim().isEmpty()) {
			for(Task item : tasks) {
				if(taskCode.trim().equals(item.getCode())) {
					return item;
				}
			}
		}
		return null;
	}

}