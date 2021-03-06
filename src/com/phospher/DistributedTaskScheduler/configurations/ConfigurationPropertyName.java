package com.phospher.DistributedTaskScheduler.configurations;

public enum ConfigurationPropertyName {

	OBJECT_PROVIDER_PROPERTY("distributedtaskconfig.objectprovider.classname"),

	CURRENT_RULE_PROPERTY("distributedtaskconfig.currentrule.code"),

	TASK_CONFIG_FILE_PATH_PROPERTY("distributedtaskconfig.filepath"),

	CURRENT_JOB_ID("distributedtaskconfig.currentjob.id"),

	TASK_JOB_RESULT_FILE("distributedtaskconfig.jobresult.filepath");

	private String _propertyName;

	private ConfigurationPropertyName(String propertyName) {
		this._propertyName = propertyName;
	}

	public String getPropertyName() {
		return this._propertyName;
	}
}