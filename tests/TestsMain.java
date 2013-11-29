import org.junit.runner.*;
import org.junit.runners.*;

public class TestsMain {
	public static void main(String[] args) {
		JUnitCore.runClasses(ConfigurationsTestSuite.class);
	}

	@RunWith(Suite.class)
	@Suite.SuiteClasses({

	})
	private class ConfigurationsTestSuite { }
}