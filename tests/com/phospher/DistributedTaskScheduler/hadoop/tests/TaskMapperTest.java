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
import org.mockito.*;
import org.mockito.stubbing.Answer;
import org.mockito.invocation.InvocationOnMock;
import com.phospher.DistributedTaskScheduler.hadoop.JobRunner;
import com.phospher.DistributedTaskScheduler.ioc.ObjectProvider;
import java.util.List;
import java.util.ArrayList;

@RunWith(JUnit4.class)
public class TaskMapperTest {


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

	public class MockObjectProvider implements ObjectProvider {
		public Object getInstance(Class<?> interClass) {
			HadoopStreamAdapter result = Mockito.mock(HadoopStreamAdapter.class);
			try {
				Mockito.when(result.getInputStream(Mockito.any(Configuration.class), Mockito.any(String.class))).then(new Answer<InputStream>() {
					public InputStream answer(InvocationOnMock invocation) {
						Configuration conf = (Configuration)invocation.getArguments()[0];
						String taskCode = conf.get(ConfigurationPropertyName.CURRENT_RULE_PROPERTY.getPropertyName());
						String jobName = conf.get(ConfigurationPropertyName.CURRENT_JOB_ID.getPropertyName()) + "_TASK_" + taskCode;
						return new StringBufferInputStream(jobName + "," + conf.get("taskresult") + "\n");
					}
				});
			} catch(IOException ex) {
			}
			return result;
		}
	}

	private void processMapTest(String className, Task[] childrenTasks, TaskResult expect, JobConf conf) throws IOException {
		Text taskCode = new Text("Test Task");
		Task task = new Task();
		task.setCode(taskCode.toString());
		task.setClassName(className);
		task.setTasks(childrenTasks);
		TaskTestCollector outputCollector = new TaskTestCollector();
		TaskMapper target = new TaskMapper(Mockito.mock(JobRunner.class));
		conf.set(ConfigurationPropertyName.CURRENT_RULE_PROPERTY.getPropertyName(), taskCode.toString());
		conf.set(ConfigurationPropertyName.OBJECT_PROVIDER_PROPERTY.getPropertyName(), "com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$MockObjectProvider");
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

	private void processChildrenTaskTest(String className, TaskResult childrenTaskResult, TaskResult expect) throws IOException {
		JobConf conf = new JobConf();
		conf.set(ConfigurationPropertyName.CURRENT_JOB_ID.getPropertyName(), "TESTJOB");
		Task[] childrenTasks = new Task[3];
		int taskArrayIndex = 0;

		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < childrenTasks.length; i++) {
			childrenTasks[i] = new Task();
			childrenTasks[i].setCode("children" + i);
		}
		conf.set("taskresult", childrenTaskResult.name());

		this.processMapTest(className, childrenTasks, expect, conf);
	}

	@Test
	public void mapTest_TaskRunningSuccessWithAllChildrenSuccess() throws IOException {
		this.processChildrenTaskTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$SuccessTaskProcess", TaskResult.SUCCESS, TaskResult.SUCCESS);
	}

	@Test
	public void mapTest_TaskRunningFailureWithAllChildrenSuccess() throws IOException {
		this.processChildrenTaskTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$FailureTaskProcess", TaskResult.SUCCESS, TaskResult.FAILURE);
	}

	@Test
	public void mapTest_TaskRunningWarningWithAllChildrenSuccess() throws IOException {
		this.processChildrenTaskTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$WarningTaskProcess", TaskResult.SUCCESS, TaskResult.WARNING);
	}

	@Test
	public void mapTest_TaskRunningExceptionWithAllChildrenSuccess() throws IOException {
		this.processChildrenTaskTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$ExceptionTaskProcess", TaskResult.SUCCESS, TaskResult.FAILURE);
	}

	@Test
	public void mapTest_TaskRunningSuccessWithAllChildrenWarning() throws IOException {
		this.processChildrenTaskTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$SuccessTaskProcess", TaskResult.WARNING, TaskResult.WARNING);
	}

	@Test
	public void mapTest_TaskRunningFailureWithAllChildrenWarning() throws IOException {
		this.processChildrenTaskTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$FailureTaskProcess", TaskResult.WARNING, TaskResult.FAILURE);
	}

	@Test
	public void mapTest_TaskRunningWarningWithAllChildrenWarning() throws IOException {
		this.processChildrenTaskTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$WarningTaskProcess", TaskResult.WARNING, TaskResult.WARNING);
	}

	@Test
	public void mapTest_TaskRunningExceptionWithAllChildrenWarning() throws IOException {
		this.processChildrenTaskTest("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$ExceptionTaskProcess", TaskResult.WARNING, TaskResult.FAILURE);
	}

	private void processAllChildrenFailure(String className) throws IOException {
		this.processChildrenTaskTest(className, TaskResult.FAILURE, TaskResult.FAILURE);
	}

	@Test
	public void mapTest_TaskRunningSuccessWithAllChildrenFailure() throws IOException {
		this.processAllChildrenFailure("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$SuccessTaskProcess");
	}

	@Test
	public void mapTest_TaskRunningFailureWithAllChildrenFailure() throws IOException {
		this.processAllChildrenFailure("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$FailureTaskProcess");
	}

	@Test
	public void mapTest_TaskRunningWarningWithAllChildrenFailure() throws IOException {
		this.processAllChildrenFailure("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$WarningTaskProcess");
	}

	@Test
	public void mapTest_TaskRunningExceptionWithAllChildrenFailure() throws IOException {
		this.processAllChildrenFailure("com.phospher.DistributedTaskScheduler.hadoop.tests.TaskMapperTest$ExceptionTaskProcess");
	}
}