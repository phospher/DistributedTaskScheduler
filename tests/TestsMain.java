import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.internal.*;

import com.phospher.DistributedTaskScheduler.Configurations.Tests.*;

public class TestsMain {
	public static void main(String[] args) {
		JUnitCore junitCore = new JUnitCore();
		junitCore.addListener(new TextListener(System.out));
		junitCore.run(ConfigurationsTestSuite.class);
	}

	@RunWith(Suite.class)
	@Suite.SuiteClasses({
		XMLTaskConfigurationProviderTest.class
	})
	private class ConfigurationsTestSuite { }
}