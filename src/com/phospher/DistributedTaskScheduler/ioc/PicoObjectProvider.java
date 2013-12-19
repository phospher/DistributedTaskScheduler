package com.phospher.DistributedTaskScheduler.ioc;

import org.picocontainer.*;
import java.util.*;
import com.phospher.DistributedTaskScheduler.configurations.*;
import com.phospher.DistributedTaskScheduler.configurations.xmlconfigurations.*;
import com.phospher.DistributedTaskScheduler.hadoop.*;

public class PicoObjectProvider implements ObjectProvider {

	private final static Class<?>[] TYPE_MAPPING = new Class<?>[] {
		HDFSFileAdapter.class,
		XMLTaskConfigurationProvider.class,
		XPathTaskConfigurationPropertyGenerator.class
	}; 

	private MutablePicoContainer _picocontainer;

	public PicoObjectProvider() {
		this._picocontainer = new DefaultPicoContainer();
		for(Class<?> item : TYPE_MAPPING) {
			this._picocontainer.addComponent(item);
		}
	}

	public Object getInstance(Class<?> interClass) {
		return this._picocontainer.getComponent(interClass);
	}
}