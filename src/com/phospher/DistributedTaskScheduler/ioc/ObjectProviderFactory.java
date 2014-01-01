package com.phospher.DistributedTaskScheduler.ioc;

import org.apache.hadoop.conf.Configuration;
import com.phospher.DistributedTaskScheduler.configurations.ConfigurationPropertyName;
import java.lang.reflect.Constructor;

public class ObjectProviderFactory {

	public static ObjectProvider getObjectProvider(Configuration configuration) throws Exception {
		return (ObjectProvider)ObjectProviderFactory.createNewInstance(configuration.get(ConfigurationPropertyName.OBJECT_PROVIDER_PROPERTY.getPropertyName()));
	}

	private static Object createNewInstance(String className) throws Exception {
		if(className == null || className.isEmpty()) {
			return null;
		}

		if(className.contains("$")) {
			String outterClassName = className.substring(0, className.indexOf('$'));
			Class outterClass = Class.forName(outterClassName);
			for(Class c : outterClass.getClasses()) {
				if(c.getName().equals(className)) {
					Constructor con = c.getConstructor(new Class[] { outterClass });
					return con.newInstance(new Object[] { outterClass.newInstance() });
				}
			}
			return null;
		} else {
			return Class.forName(className).newInstance();
		}
	}
}