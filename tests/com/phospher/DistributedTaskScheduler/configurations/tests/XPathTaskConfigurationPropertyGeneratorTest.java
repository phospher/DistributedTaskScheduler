package com.phospher.DistributedTaskScheduler.configurations.tests;

import org.junit.*;
import com.phospher.DistributedTaskScheduler.configurations.*;
import java.util.*;
import org.mockito.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.apache.hadoop.conf.*;

@RunWith(JUnit4.class)
public class XPathTaskConfigurationPropertyGeneratorTest {

	private List<Task> _testData;

	private Task processSearchRootTask(String taskCode) {
		this._testData = new ArrayList<Task>();
		for(int i = 0; i < 3; i++) {
			Task item = new Task();
			item.setCode("Code" + i);
			this._testData.add(item);
		}

		TaskConfiguration testTaskConfiguration = Mockito.mock(TaskConfiguration.class);
		Mockito.when(testTaskConfiguration.getTasks()).thenReturn(this._testData);
		return new XPathTaskConfigurationPropertyGenerator().searchTask(testTaskConfiguration, taskCode);
	}

	@Test
	public void searTaskTest_SearchRootTask() {
		Task actual = this.processSearchRootTask("Code1");
		Assert.assertEquals("faliure - return wrong task", this._testData.get(1), actual);
	}

	@Test
	public void searchTaskTest_SearchRootFailure() {
		Task actual = this.processSearchRootTask("Code4");
		Assert.assertNull("faliure - do not return null", actual);
	}

	private Task processSearchChildTask(String taskCode) {
		this._testData = new ArrayList<Task>();
		for(int i = 0; i < 3; i++) {
			Task lev1 = new Task();
			lev1.setCode("Code" + i);
			lev1.setTasks(new Task[3]);
			for(int j = 0; j < 3; j++) {
				Task lev2 = new Task();
				lev2.setCode("Code" + i + j);
				lev2.setTasks(new Task[3]);
				for(int k = 0; k < 3; k++) {
					Task lev3 = new Task();
					lev3.setCode("Code" + i + j + k);
					lev2.getTasks()[k] = lev3;
				}
				lev1.getTasks()[j] = lev2;
			}
			this._testData.add(lev1);
		}

		TaskConfiguration testTaskConfiguration = Mockito.mock(TaskConfiguration.class);
		Mockito.when(testTaskConfiguration.getTasks()).thenReturn(this._testData);
		return new XPathTaskConfigurationPropertyGenerator().searchTask(testTaskConfiguration, taskCode);
	}

	@Test
	public void searchTaskTest_SearchLeafTask() {
		Task actual = this.processSearchChildTask("Code2/Code21/Code210");
		Assert.assertEquals("failure - do not search the corrent task", this._testData.get(2).getTasks()[1].getTasks()[0], actual);
	}

	@Test
	public void searchTaskTest_SearchLeafTaskFailure() {
		Task actual = this.processSearchChildTask("Code2/Code21/Code214");
		Assert.assertNull("failure - do not return null", actual);
	}

	@Test
	public void searchTaskTest_SearchFormalTask() {
		Task actual = this.processSearchChildTask("Code1/Code10");
		Assert.assertEquals("failure - do not search the corrent task", this._testData.get(1).getTasks()[0], actual);
	}

	@Test
	public void searchTaskTest_SearchFormalTaskFailure() {
		Task actual = this.processSearchChildTask("Code1/Code14");
		Assert.assertNull("failure - do not return null", actual);
	}

	private String processGenerateTaskCodeTest(String currentTaskCode, String taskCode) {
		Configuration configuration = new Configuration();
		if(currentTaskCode != null) {
			configuration.set(ConfigurationPropertyName.CURRENT_RULE_PROPERTY.getPropertyName(), currentTaskCode);
		}
		return new XPathTaskConfigurationPropertyGenerator().generateTaskCode(configuration, taskCode);
	}

	@Test
	public void generateTaskCodeTest_CurrentCodeIsNotEmpty() {
		String actual = processGenerateTaskCodeTest("Code1/Code2", "Code3");
		Assert.assertEquals("failures - do not return the correct result", "Code1/Code2/Code3", actual);
	}

	@Test
	public void generateTaskCodeTest_CurrentCodeIsEmpty() {
		String actual = processGenerateTaskCodeTest("", "Code3");
		Assert.assertEquals("failure - do not return the correct result", "Code3", actual);
	}

	@Test
	public void generateTaskCodeTest_CurrentCodeNotExits() {
		String actual = processGenerateTaskCodeTest(null, "Code3");
		Assert.assertEquals("failure - do not return the correct result", "Code3", actual);
	}

}