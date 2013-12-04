package com.phospher.DistributedTaskScheduler.ioc.tests;

import org.junit.*;
import org.mockito.*;
import org.junit.runner.*;
import org.junit.runners.*;
import com.phospher.DistributedTaskScheduler.ioc.*;
import com.phospher.DistributedTaskScheduler.configurations.*;
import com.phospher.DistributedTaskScheduler.configurations.xmlconfigurations.*;

@RunWith(JUnit4.class)
public class PicoObjectProviderTest {

	@Test
	public void getInstanceTest_GetCorrectType() {
		PicoObjectProvider target = new PicoObjectProvider();
		Object actual = target.getInstance(TaskConfigurationProvider.class);
		Assert.assertEquals("failure - did not get the correct type", XMLTaskConfigurationProvider.class, actual.getClass());
	}

}