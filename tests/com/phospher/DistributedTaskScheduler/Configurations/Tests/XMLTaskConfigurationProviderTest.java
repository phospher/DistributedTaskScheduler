package com.phospher.DistributedTaskScheduler.Configurations.Tests;

import org.junit.*;
import com.phospher.DistributedTaskScheduler.Configurations.*;
import com.phospher.DistributedTaskScheduler.Configurations.XMLConfiguration.*;
import com.phospher.DistributedTaskScheduler.File.*;
import org.mockito.*;
import java.io.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class XMLTaskConfigurationProviderTest {

	private TaskConfiguration processTest() throws Exception {
		String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><TaskConfiguration>"
			+ "<Tasks><Task code=\"Task1\" name=\"Task1\" className=\"com.phospher.Test\">"
			+ "<TaskArgs>arg0 arg1  arg2</TaskArgs><PrevTasks><Task code=\"Task2\" name=\"Task2\" "
			+ "className=\"com.phospher.Test2\"><PrevTasks><Task code=\"Task3\" name=\"Task3\" "
			+ "className=\"com.phospher.Test3\" /></PrevTasks></Task><Task code=\"Task4\" name=\""
			+ "Task4\" className=\"com.phospher.Test4\" /></PrevTasks></Task><Task code=\"Task5\" "
			+ "name=\"Task5\" className=\"com.phospher.Test5\" /></Tasks></TaskConfiguration>";
		StringBufferInputStream is = new StringBufferInputStream(testXml);

		FileAdapter mockFileAdapter = Mockito.mock(FileAdapter.class);
		Mockito.when(mockFileAdapter.readFile(Mockito.anyString())).thenReturn(is);

		XMLTaskConfigurationProvider target = new XMLTaskConfigurationProvider(mockFileAdapter, null);
		return target.getConfiguration();
	}

	@Test
	public void getConfigurationTest_AssertTaskRootCount() throws Exception {
		TaskConfiguration target = this.processTest();
		Assert.assertEquals("failure - count of root tasks is not correct", 2, target.getTasks().size());
	}

	@Test
	public void getConfigurationTest_AssertPrevTasksCount() throws Exception {
		Task target = this.processTest().getTasks().get(0);
		Assert.assertEquals("failure - count of prevTasks is not correct", 2, target.getTasks().size());
	}

	@Test
	public void getConfigurationTest_AssertTask1Name() throws Exception {
		Task target = this.processTest().getTasks().get(0);
		Assert.assertEquals("failure - Task1's name is not correct", "Task1", target.getName());
	}

	@Test
	public void getConfigurationTest_AssertArgs() throws Exception {
		Task target = this.processTest().getTasks().get(0);
		Assert.assertTrue("failure - Task1's arguments are not correct", (target.getArgs()[0].equals("arg0") && target.getArgs()[1].equals("arg1") && target.getArgs()[2].equals("arg2")));
	}
}