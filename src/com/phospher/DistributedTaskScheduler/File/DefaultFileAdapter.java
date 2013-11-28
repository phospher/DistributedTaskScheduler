package com.phospher.DistributedTaskScheduler.File;

import java.io.*;
import java.lang.*;

public class DefaultFileAdapter implements FileAdapter {

	public InputStream readFile(String path) throws FileNotFoundException, SecurityException {
		return new FileInputStream(path);
	}

}