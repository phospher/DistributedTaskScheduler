package com.phospher.DistributedTaskScheduler.Configurations.XMLConfiguration;

import com.phospher.DistributedTaskScheduler.Configurations.*;
import com.phospher.DistributedTaskScheduler.File.*;
import java.io.*;
import javax.xml.bind.*;

public class XMLTaskConfigurationProvider implements TaskConfigurationProvider {

	private FileAdapter _fileAdapter;
	private String _path;

	public XMLTaskConfigurationProvider(FileAdapter fileAdapter, String path) {
		this._fileAdapter = fileAdapter;
		this._path = path;
	}

	public TaskConfiguration getConfiguration() throws Exception {
		InputStream is = this._fileAdapter.readFile(this._path);
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