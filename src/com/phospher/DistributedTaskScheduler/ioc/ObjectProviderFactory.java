package com.phospher.DistributedTaskScheduler.ioc;

import org.apache.hadoop.conf.Configuration;
import com.phospher.DistributedTaskScheduler.configurations.ConfigurationPropertyName;
import com.phospher.DistributedTaskScheduler.util.ObjectUtil;

public class ObjectProviderFactory {

	public static ObjectProvider getObjectProvider(Configuration configuration) throws Exception {
		return (ObjectProvider)ObjectUtil.createInstance(configuration.get(ConfigurationPropertyName.OBJECT_PROVIDER_PROPERTY.getPropertyName()));
	}
}