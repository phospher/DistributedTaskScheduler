package com.phospher.DistributedTaskScheduler.hadoop.tests;

import org.junit.*;
import java.io.*;
import org.junit.runner.*;
import org.junit.runners.*;
import com.phospher.DistributedTaskScheduler.hadoop.*;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.io.Text;
import java.util.HashMap;
import com.phospher.DistributedTaskScheduler.TaskProcess;
import com.phospher.DistributedTaskScheduler.configurations.Task;

@RunWith(JUnit4.class)
public class TaskMapperTest {

	public class TaskTestCollector implements OutputCollector<Text, TaskRunningResult> {

		private HashMap<Text, TaskRunningResult> _data;

		public TaskTestCollector() {
			this._data = new HashMap<Text, TaskRunningResult>();
		}

		public TaskRunningResult get(Text key) {
			return this._data.get(key);
		}

		public void collect(Text key, TaskRunningResult value) throws IOException {
			this._data.put(key, value);
		}

	}

	public class SuccessTaskProcess implements TaskProcess {
		public TaskResult run(String[] args) throws Exception {
			return TaskResult.SUCCESS;
		}
	}

	public class FailureTaskProcess implements TaskProcess {
		public TaskResult run(String[] args) throws Exception {
			return TaskResult.FAILURE;
		}
	}

	public class ExceptionTaskProcess implements TaskProcess {
		public TaskResult run(String[] args) throws Exception {
			throw new Exception();
		}
	}

	private void processMapTest(String className, Task[] childrenTasks, TaskResult expect) throws IOException {
		Text taskCode = new Text("Test Task");
		Task task = new Task();
		task.setCode(taskCode.toString());
		task.setClassName(className);
		task.setTasks(childrenTasks);
		TaskTestCollector outputCollector = new TaskTestCollector();
		new TaskMapper().map(taskCode, task, outputCollector, null);
		Assert.assertEquals("failure - Do not return the correct result", expect, outputCollector.get(taskCode).getResult());
	}
	
	@Test
	public void mapTest_TaskRuningSuccessWithoutChildren() throws IOException {
		this.processMapTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$SuccessTaskProcess", null, TaskResult.SUCCESS);
	}

	@Test
	public void mapTest_TaskRunningFailureWithoutChildren() throws IOException {
		this.processMapTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$FailureTaskProcess", null, TaskResult.FAILURE);
	}

	@Test
	public void mapTest_TaskRunningExceptionWithoutChildren() throws IOException {
		this.processMapTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$ExceptionTaskProcess", null, TaskResult.FAILURE);
	}
}