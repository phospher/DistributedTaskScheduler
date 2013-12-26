package com.phospher.DistributedTaskScheduler.hadoop.tests;

import org.junit.*;
import com.phospher.DistributedTaskScheduler.hadoop.*;
import java.io.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class TaskFeedBackTest {

	@Test
	public void testTaskFeedBackSerialization() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		TaskRunningResult param = new TaskRunningResult();
		param.setResult(TaskResult.SUCCESS);
		param.setMessage("Test Message");
		param.setException(new Exception("test exception"));
		TaskFeedBack target = new TaskFeedBack();
		target.setResult(param);
		target.setTaskCode("TestCode");
		target.write(new DataOutputStream(out));
		ByteArrayInputStream bain = new ByteArrayInputStream(out.toByteArray());
		TaskFeedBack actual = new TaskFeedBack();
		actual.readFields(new DataInputStream(bain));
		Assert.assertEquals("failure - not return the same object", target.getTaskCode(), actual.getTaskCode());
	}

}