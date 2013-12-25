package com.phospher.DistributedTaskScheduler.hadoop.tests;

import org.junit.*;
import com.phospher.DistributedTaskScheduler.hadoop.*;
import java.io.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class TaskRunningResultTest {

	@Test
	public void testTaskRunningResultSerialization() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		TaskRunningResult target = new TaskRunningResult();
		target.setResult(TaskResult.SUCCESS);
		target.setMessage("Test Message");
		target.setException(new Exception("test exception"));
		target.write(new DataOutputStream(out));
		ByteArrayInputStream bain = new ByteArrayInputStream(out.toByteArray());
		TaskRunningResult actual = new TaskRunningResult();
		actual.readFields(new DataInputStream(bain));
		Assert.assertEquals("failure - not return the same object", target.getResult(), actual.getResult());
	}

}