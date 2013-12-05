package com.phospher.DistributedTaskScheduler.configurations.xmlconfigurations;

import com.phospher.DistributedTaskScheduler.configurations.*;
import com.phospher.DistributedTaskScheduler.hadoop.*;
import java.io.*;
import javax.xml.bind.*;
import org.apache.hadoop.conf.*;

public class XMLTaskConfigurationProvider implements TaskConfigurationProvider {

	private HadoopStreamAdapter _steamAdapter;

	public XMLTaskConfigurationProvider(HadoopStreamAdapter steamAdapter) {
		this._steamAdapter = steamAdapter;
	}

	public TaskConfiguration getConfiguration(Configuration conf) throws Exception {
		InputStream is = this._steamAdapter.getInputStream(conf);
		JAXBContext jaxbContext = JAXBContext.newInstance(XMLTaskConfiguration.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Object result = unmarshaller.unmarshal(is);
		if (result instanceof XMLTaskConfiguration) {
			return (XMLTaskConfiguration)result;
		} else {
			return null;
		}
	}
}