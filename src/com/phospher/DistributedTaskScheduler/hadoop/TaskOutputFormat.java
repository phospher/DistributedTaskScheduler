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
import org.apache.hadoop.mapred.Reporter;

public class TaskOutputFormat implements OutputFormat<Text, TaskRunningResult> {

	public static class TaskRecordWriter implements RecordWriter<Text, TaskRunningResult> {

		private Configuration _conf;

		public TaskRecordWriter(Configuration conf) {
			this._conf = conf;
		}

		public static HadoopStreamAdapter getStreamAdapter(Configuration conf) throws Exception {
			ObjectProvider objectProvider = ObjectProviderFactory.getObjectProvider(conf);
			HadoopStreamAdapter streamAdapter = (HadoopStreamAdapter)objectProvider.getInstance(HadoopStreamAdapter.class);
			return streamAdapter;
		}
		
		public void close(Reporter reporter) throws IOException { }

		public void write(Text key, TaskRunningResult value) throws IOException {
			try {
				HadoopStreamAdapter streamAdapter = this.getStreamAdapter(this._conf);
				String text = key.toString() + "," + value.getResult().name();
				streamAdapter.append(this._conf, ConfigurationPropertyName.TASK_JOB_RESULT_FILE.getPropertyName(), text);
			} catch(IOException ex) {
				throw ex;
			} catch(Exception ex) {
				throw new IOException(ex);
			}
		}
	}

	public void checkOutputSpecs(FileSystem ignored, JobConf job) throws IOException {	}

	public RecordWriter<Text, TaskRunningResult> getRecordWriter(FileSystem ignored, JobConf job, String name, Progressable progress) throws IOException {
		try {
			HadoopStreamAdapter streamAdapter = TaskRecordWriter.getStreamAdapter(job);
			if(!streamAdapter.exists(job, ConfigurationPropertyName.TASK_JOB_RESULT_FILE.getPropertyName())) {
				streamAdapter.createOrReplace(job, ConfigurationPropertyName.TASK_JOB_RESULT_FILE.getPropertyName());
			}
			return new TaskRecordWriter(job);
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new IOException(ex);
		}
	}

}