package com.phospher.DistributedTaskScheduler.File;

import java.io.*;

public interface FileAdapter {
	InputStream readFile(String path) throws FileNotFoundException, SecurityException;
}