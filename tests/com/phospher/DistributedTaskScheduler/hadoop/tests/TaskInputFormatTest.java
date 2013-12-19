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

	private int getTaskCount(InputSplit[] inputSplits) throws Exception {
		int taskCount = 0;
		for(InputSplit item : inputSplits) {
			taskCount += item.getLength();
		}
		return taskCount;
	}

	private void assertCountOfInputSplit(int taskCount, int numSplit, int expect) throws Exception {
		InputSplit[] actual = this.getSplitsTestProcess(taskCount, numSplit);
		Assert.assertEquals("failure - did not return the correct count of InputSplit", expect, actual.length);
	}

	private void assertContainsAllTask(int taskCount, int numSplit) throws Exception {
		InputSplit[] actual = this.getSplitsTestProcess(taskCount, numSplit);
		Assert.assertEquals("failure - did not return the correct count of Task", taskCount, this.getTaskCount(actual));
	}

	@Test
	public void getSplitsTest_TaskCountLessThenNumSplit_AssertCountOfInputSplit() throws Exception {
		this.assertCountOfInputSplit(3, 4, 3);
	}

	@Test
	public void getSplitsTest_TaskCountLessThenNumSplit_ContainsAllTask() throws Exception {
		this.assertContainsAllTask(3, 4);
	}

	@Test
	public void getSplitsTest_TaskCountMoreThenNumSplit_AssertCountOfInputSplit() throws Exception {
		this.assertCountOfInputSplit(6, 4, 4);
	}

	@Test
	public void getSplitsTest_TaskCountMoreThenNumSplit_ContainsAllTask() throws Exception {
		this.assertContainsAllTask(6, 4);
	}

	@Test
	public void getSplitsTest_TaskCountEqualsNumSplit_AssertCountOfInputSplit() throws Exception {
		this.assertCountOfInputSplit(4, 4, 4);
	}

	@Test
	public void getSplitsTest_TaskCountEqualsNumSplit_ContainsAllTask() throws Exception {
		this.assertContainsAllTask(4, 4);
	}

	@Test
	public void getSplitsTest_TaskCountTwiceofNumSplit_AssertCountOfInputSplit() throws Exception {
		this.assertCountOfInputSplit(8, 4, 4);
	}

	@Test
	public void getSplitsTest_TaskCountTwiceOfNumSplit_ContainsAllTask() throws Exception {
		this.assertContainsAllTask(8, 4);
	}

}