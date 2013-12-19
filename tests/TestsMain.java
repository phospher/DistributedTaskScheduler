import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.internal.*;

import com.phospher.DistributedTaskScheduler.configurations.tests.*;
import com.phospher.DistributedTaskScheduler.ioc.tests.*;
import com.phospher.DistributedTaskScheduler.hadoop.tests.*;

public class TestsMain {
	public static void main(String[] args) {
		JUnitCore junitCore = new JUnitCore();
		junitCore.addListener(new TextListener(System.out));
		junitCore.run(ConfigurationsTestSuite.class, IOCTestSuite.class, HadoopTestSuite.class);
	}

	@RunWith(Suite.class)
	@Suite.SuiteClasses({
		XMLTaskConfigurationProviderTest.class,
		TaskTest.class,
		XPathTaskConfigurationPropertyGeneratorTest.class
	})
	private class ConfigurationsTestSuite { }

	@RunWith(Suite.class)
	@Suite.SuiteClasses({
		PicoObjectProviderTest.class
	})
	private class IOCTestSuite { }

	@RunWith(Suite.class)
	@Suite.SuiteClasses({
		TaskInputFormatTest.class
	})
	private class HadoopTestSuite { }

}