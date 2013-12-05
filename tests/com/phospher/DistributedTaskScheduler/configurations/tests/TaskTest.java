package com.phospher.DistributedTaskScheduler.configurations.tests;

import org.junit.*;
import com.phospher.DistributedTaskScheduler.configurations.*;
import org.apache.hadoop.io.*;
import org.mockito.*;
import java.io.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class TaskTest {

	private Task _target;

	@Before
	public void setUp() {
		this._target = new Task();
		this._target.setCode("code1");
		this._target.setName("name1");
		this._target.setClassName("classname1");
		this._target.setTasks(new Task[0]);
		this._target.setArgs(new String[0]);
	}

	@Test
	public void writeTest() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		this._target.write(new DataOutputStream(out));
		Assert.assertTrue("failure - output is empty", out.toByteArray().length > 0);
	}

	@Test
	public void readFieldsTest() throws IOException {
		ByteArrayOutputStream baout = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(baout);
		oout.writeObject(this._target);

		ByteArrayInputStream bain = new ByteArrayInputStream(baout.toByteArray());
		Task actual = new Task();
		actual.readFields(new DataInputStream(bain));
		Assert.assertEquals("failure - task code is not correct", "code1", actual.getCode());
	}

}