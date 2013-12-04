package com.phospher.DistributedTaskScheduler.file;

import java.io.*;

public interface FileAdapter {
	InputStream readFile(String path) throws FileNotFoundException, SecurityException;
}