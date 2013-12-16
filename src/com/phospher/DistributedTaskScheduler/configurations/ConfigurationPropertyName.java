package com.phospher.DistributedTaskScheduler.configurations;

public enum ConfigurationPropertyName {

	OBJECT_PROVIDER_PROPERTY("distributedtaskconfig.objectprovider.classname"),

	CURRENT_RULE_PROPERTY("distributedtaskconnfig.currentrule.code");

	private String _propertyName;

	private ConfigurationPropertyName(String propertyName) {
		this._propertyName = propertyName;
	}

	public String getPropertyName() {
		return this._propertyName;
	}
}