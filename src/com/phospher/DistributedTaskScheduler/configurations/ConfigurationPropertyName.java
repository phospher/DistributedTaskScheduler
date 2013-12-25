package com.phospher.DistributedTaskScheduler.configurations;

public enum ConfigurationPropertyName {

	OBJECT_PROVIDER_PROPERTY("distributedtaskconfig.objectprovider.classname"),

	CURRENT_RULE_PROPERTY("distributedtaskconnfig.currentrule.code"),

	TASK_CONFIG_FILE_PATH_PROPERTY("distributedtaskconfig.filepath");

	private String _propertyName;

	private ConfigurationPropertyName(String propertyName) {
		this._propertyName = propertyName;
	}

	public String getPropertyName() {
		return this._propertyName;
	}
}