package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.conf.*;

public class ObjectProvider {

	private Configuration _conf;

	public ObjectProvider(Configuration conf) {
		this._conf = conf;
	}

	protected Configuration getConf() {
		return this._conf;
	}

	public void addClass(Class<?> interClass, Class<?> implClass) {
		this._conf.setClass(interClass.getName(), implClass, interClass);
	}

	public Class<?> getClass(Class<?> interClass) {
		return this._conf.getClass(interClass.getName(), null);
	}
}