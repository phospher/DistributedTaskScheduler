package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.io.*;
import com.phospher.DistributedTaskScheduler.configurations.*;
import com.phospher.DistributedTaskScheduler.ioc.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.Constructor;

public class TaskInputFormat implements InputFormat<Text, Task> {

	public RecordReader<Text, Task> getRecordReader(InputSplit split, JobConf conf, Reporter reporter) throws IOException {
		TaskInputSplit inputSplit = (TaskInputSplit)split;
		return new TaskRecordReader(inputSplit.getTasks());
	}

	public InputSplit[] getSplits(JobConf conf, int numSplits) throws IOException {
		try {
			ObjectProvider objectProvider = (ObjectProvider)this.createNewInstance(conf.get(ConfigurationPropertyName.OBJECT_PROVIDER_PROPERTY.getPropertyName()));
			TaskConfigurationProvider configurationProvider = (TaskConfigurationProvider)objectProvider.getInstance(TaskConfigurationProvider.class);
			TaskConfiguration taskConfiguration = configurationProvider.getConfiguration(conf);
			TaskConfigurationPropertyGenerator propertyGenerator = (TaskConfigurationPropertyGenerator)objectProvider.getInstance(TaskConfigurationPropertyGenerator.class);
			String taskCode = conf.get(ConfigurationPropertyName.CURRENT_RULE_PROPERTY.getPropertyName());
			Task currentTask = propertyGenerator.searchTask(taskConfiguration, taskCode);
			int splitSize = currentTask.getTasks().length / numSplits;
			int inputSplitCount = splitSize > 0 ? numSplits : currentTask.getTasks().length;
			ArrayList<ArrayList<Task>> taskArraies = new ArrayList<ArrayList<Task>>(inputSplitCount);
			for(int i = 0; i < inputSplitCount; i++) {
				taskArraies.add(new ArrayList<Task>());
			}
			int taskArraiesIndex = 0;
			for(int i = 0; i < currentTask.getTasks().length; i++) {
				taskArraies.get(taskArraiesIndex).add(currentTask.getTasks()[i]);
				if((++taskArraiesIndex) >= taskArraies.size()) {
					taskArraiesIndex = 0;
				}
			}
			InputSplit[] result = new InputSplit[taskArraies.size()];
			for(int i = 0; i < result.length; i++) {
				Task[] tasks = new Task[taskArraies.get(i).size()];
				tasks = taskArraies.get(i).toArray(tasks);
				result[i] = new TaskInputSplit(tasks);
			}
			return result;
		} catch(Exception ex) {
			throw new IOException(ex);
		}
	}

	private Object createNewInstance(String className) throws Exception {
		if(className == null || className.isEmpty()) {
			return null;
		}

		if(className.contains("$")) {
			String outterClassName = className.substring(0, className.indexOf('$'));
			Class outterClass = Class.forName(outterClassName);
			for(Class c : outterClass.getClasses()) {
				if(c.getName().equals(className)) {
					Constructor con = c.getConstructor(new Class[] { outterClass });
					return con.newInstance(new Object[] { outterClass.newInstance() });
				}
			}
			return null;
		} else {
			return Class.forName(className).newInstance();
		}
	}

}