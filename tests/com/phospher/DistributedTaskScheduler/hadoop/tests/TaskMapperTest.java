package com.phospher.DistributedTaskScheduler.hadoop.tests;

import org.junit.*;
import java.io.*;
import org.junit.runner.*;
import org.junit.runners.*;
import com.phospher.DistributedTaskScheduler.hadoop.*;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.io.Text;
import java.util.HashMap;
import java.util.Map;
import com.phospher.DistributedTaskScheduler.TaskProcess;
import com.phospher.DistributedTaskScheduler.configurations.Task;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobConf;
import com.phospher.DistributedTaskScheduler.configurations.ConfigurationPropertyName;

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

	public class WarningTaskProcess implements TaskProcess {
		public TaskResult run(String[] args) throws Exception {
			return TaskResult.WARNING;
		}
	}

	public class MockHadoopStreamAdapter implements HadoopStreamAdapter {
		public InputStream getInputStream(Configuration conf, String filePathPropertyName) throws IOException {
			String taskResultString = conf.get("taskresult");
			StringBuilder resultString = new StringBuilder();
			for(String item : taskResultString.split(";")) {
				String[] taskResult = item.split(":");
				resultString.append(taskResult[0]);
				resultString.append(",");
				resultString.append(taskResult[1]);
				resultString.append("\n");
			}
			return new StringBufferInputStream(resultString.toString());
		}

		public void append(Configuration conf, String filePathPropertyName, String text) throws IOException {
		}

		public void createOrReplace(Configuration conf, String filePathPropertyName) throws IOException {
		}
	}

	private void processMapTest(String className, Task[] childrenTasks, TaskResult expect, JobConf conf) throws IOException {
		Text taskCode = new Text("Test Task");
		Task task = new Task();
		task.setCode(taskCode.toString());
		task.setClassName(className);
		task.setTasks(childrenTasks);
		TaskTestCollector outputCollector = new TaskTestCollector();
		TaskMapper target = new TaskMapper();
		conf.set(ConfigurationPropertyName.CURRENT_RULE_PROPERTY.getPropertyName(), taskCode.toString());
		target.configure(conf);
		target.map(taskCode, task, outputCollector, null);
		Assert.assertEquals("failure - Do not return the correct result", expect, outputCollector.get(taskCode).getResult());
	}
	
	@Test
	public void mapTest_TaskRuningSuccessWithoutChildren() throws IOException {
		this.processMapTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$SuccessTaskProcess", null, TaskResult.SUCCESS, new JobConf());
	}

	@Test
	public void mapTest_TaskRunningFailureWithoutChildren() throws IOException {
		this.processMapTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$FailureTaskProcess", null, TaskResult.FAILURE, new JobConf());
	}

	@Test
	public void mapTest_TaskRunningExceptionWithoutChildren() throws IOException {
		this.processMapTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$ExceptionTaskProcess", null, TaskResult.FAILURE, new JobConf());
	}

	@Test
	public void mapTest_TaskRunningWarningWithoutChildren() throws IOException {
		this.processMapTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$WarningTaskProcess", null, TaskResult.WARNING, new JobConf());
	}

	private void processChildrenTaskTest(String className, Map<String, TaskResult> childrenTasksResult, TaskResult expect) throws IOException {
		JobConf conf = new JobConf();
		conf.set(ConfigurationPropertyName.CURRENT_JOB_ID.getPropertyName(), "TESTJOB");
		Task[] childrenTasks = new Task[childrenTasksResult.size()];
		int taskArrayIndex = 0;

		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, TaskResult> item : childrenTasksResult.entrySet()) {
			sb.append("TESTJOB_TASK_" + item.getKey());
			sb.append(":");
			sb.append(item.getValue().name());
			sb.append(";");

			childrenTasks[taskArrayIndex] = new Task();
			childrenTasks[taskArrayIndex].setCode(item.getKey());
		}
		conf.set("taskresult", sb.toString());

		this.processMapTest(className, childrenTasks, expect, conf);
	}
}