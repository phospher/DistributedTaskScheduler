package com.phospher.DistributedTaskScheduler.hadoop.tests;

import org.junit.*;
import com.phospher.DistributedTaskScheduler.configurations.*;
import com.phospher.DistributedTaskScheduler.hadoop.*;
import com.phospher.DistributedTaskScheduler.ioc.*;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.*;
import org.mockito.*;
import org.mockito.stubbing.*;
import org.mockito.invocation.*;
import java.io.*;
import java.util.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class TaskInputFormatTest {

	private class MockTaskConfigurationProvider implements TaskConfigurationProvider {

		public TaskConfiguration getConfiguration(Configuration conf) throws Exception {
			int taskCount = conf.getInt("taskCount", 0);
			final Task task = new Task();
			task.setCode("TestTask");
			task.setTasks(new Task[taskCount]);
			for(int i = 0; i < taskCount; i++) {
				task.getTasks()[i] = new Task();
				task.getTasks()[i].setCode("Code" + i);
			}
			TaskConfiguration result = Mockito.mock(TaskConfiguration.class);
			Mockito.when(result.getTasks()).thenReturn(new ArrayList<Task>() {{
				add(task);
			}});
			return result;
		}

	}

	public class MockObjectProvider implements ObjectProvider {

		public Object getInstance(Class<?> interClass) {
			if(TaskConfigurationProvider.class.equals(interClass)) {
				return this.getTaskConfigurationProvider();
			} else if(TaskConfigurationPropertyGenerator.class.equals(interClass)) {
				return this.getTaskConfigurationPropertyGenerator();
			} else {
				return null;
			}
		}

		private TaskConfigurationProvider getTaskConfigurationProvider() {
			return new MockTaskConfigurationProvider();
		}

		private TaskConfigurationPropertyGenerator getTaskConfigurationPropertyGenerator() {
			TaskConfigurationPropertyGenerator result = Mockito.mock(TaskConfigurationPropertyGenerator.class);
			Mockito.when(result.searchTask(Mockito.any(TaskConfiguration.class), Mockito.any(String.class))).then(new Answer<Task>() {
				public Task answer(InvocationOnMock invocation) {
					TaskConfiguration taskConfiguration = (TaskConfiguration)invocation.getArguments()[0];
					return taskConfiguration.getTasks().get(0);
				}
			});
			return result;
		}

	}

	private InputSplit[] getSplitsTestProcess(int taskCount, int numSplit) throws IOException {
		JobConf conf = new JobConf();
		conf.set("taskCount", String.valueOf(taskCount));
		conf.set(ConfigurationPropertyName.OBJECT_PROVIDER_PROPERTY.getPropertyName(), "com.phospher.DistributedTaskScheduler.hadoop.tests.TaskInputFormatTest$MockObjectProvider");
		conf.set(ConfigurationPropertyName.CURRENT_RULE_PROPERTY.getPropertyName(), "");
		return new TaskInputFormat().getSplits(conf, numSplit);
	}

	@Test
	public void getSplitsTest_TaskCountLessThenNumSplit_AssertCountOfInputSplit() throws Exception {
		InputSplit[] actual = this.getSplitsTestProcess(3, 4);
		Assert.assertEquals("failure - did not return the correct count of InputSplit", 3, actual.length);
	}

	@Test
	public void getSplitsTest_TaskCountLessThenNumSplit_ContainsAllTask() throws Exception {
		InputSplit[] actual = this.getSplitsTestProcess(3, 4);
		int taskCount = 0;
		for(InputSplit item : actual) {
			taskCount += item.getLength();
		}
		Assert.assertEquals("failure - did not return the correct count of Task", 3, actual.length);
	}

}