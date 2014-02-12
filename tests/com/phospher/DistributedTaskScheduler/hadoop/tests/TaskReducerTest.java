package com.phospher.DistributedTaskScheduler.hadoop.tests;

import org.junit.*;
import com.phospher.DistributedTaskScheduler.hadoop.*;
import org.apache.hadoop.io.Text;
import java.io.*;
import org.junit.runner.*;
import org.junit.runners.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

@RunWith(JUnit4.class)
public class TaskReducerTest {

	private void processTest(List<TaskRunningResult> data, TaskResult expect) throws IOException {
		Text key = new Text("testTaskCode");
		TaskReducer target = new TaskReducer();
		TaskTestCollector collector = new TaskTestCollector();
		target.reduce(key, data.iterator(), collector, null);
		Assert.assertEquals("failure - Do not return the correct result", expect, collector.get(key).getResult());
	}

	@Test
	public void reduceTest_AllRuningResultSuccess() throws IOException {
		ArrayList<TaskRunningResult> data = new ArrayList<TaskRunningResult>();
		for(int i = 0; i < 3; i++) {
			TaskRunningResult item = new TaskRunningResult();
			item.setResult(TaskResult.SUCCESS);
			data.add(item);
		}
		this.processTest(data, TaskResult.SUCCESS);
	}

	@Test
	public void reduceTest_OneRunningResultWarning() throws IOException {
		ArrayList<TaskRunningResult> data = new ArrayList<TaskRunningResult>();

		TaskRunningResult item = new TaskRunningResult();
		item.setResult(TaskResult.SUCCESS);
		data.add(item);

		item = new TaskRunningResult();
		item.setResult(TaskResult.WARNING);
		data.add(item);

		item = new TaskRunningResult();
		item.setResult(TaskResult.SUCCESS);
		data.add(item);

		this.processTest(data, TaskResult.WARNING);
	}

	@Test
	public void reduceTest_OneRunningResultFailure() throws IOException {
		ArrayList<TaskRunningResult> data = new ArrayList<TaskRunningResult>();

		TaskRunningResult item = new TaskRunningResult();
		item.setResult(TaskResult.SUCCESS);
		data.add(item);

		item = new TaskRunningResult();
		item.setResult(TaskResult.FAILURE);
		data.add(item);

		item = new TaskRunningResult();
		item.setResult(TaskResult.WARNING);
		data.add(item);

		this.processTest(data, TaskResult.FAILURE);
	}
}