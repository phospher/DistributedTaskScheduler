package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.mapred.OutputFormat;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.util.Progressable;
import org.apache.hadoop.io.Text;
import java.io.IOException;
import com.phospher.DistributedTaskScheduler.configurations.*;
import com.phospher.DistributedTaskScheduler.ioc.*;
import org.apache.hadoop.conf.Configuration;

public class TaskOutputFormat implements OutputFormat<Text, TaskRunningResult> {

	private HadoopStreamAdapter getStreamAdapter(Configuration conf) throws Exception {
		ObjectProvider objectProvider = ObjectProviderFactory.getObjectProvider(conf);
		HadoopStreamAdapter streamAdapter = (HadoopStreamAdapter)objectProvider.getInstance(HadoopStreamAdapter.class);
		return streamAdapter;
	}

	//checkOutputSpecs is to create the file for storing the task running result
	public void checkOutputSpecs(FileSystem ignored, JobConf job) throws IOException {
		try {
			HadoopStreamAdapter streamAdapter = this.getStreamAdapter(job);
			if(streamAdapter.exists(job, ConfigurationPropertyName.TASK_JOB_RESULT_FILE.getPropertyName())) {
				streamAdapter.createOrReplace(job, ConfigurationPropertyName.TASK_JOB_RESULT_FILE.getPropertyName());
			}
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new IOException(ex);
		}
	}

	public RecordWriter<Text, TaskRunningResult> getRecordWriter(FileSystem ignored, JobConf job, String name, Progressable progress) throws IOException {
		return null;
	}

}