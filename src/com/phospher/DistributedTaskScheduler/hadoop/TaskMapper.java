package com.phospher.DistributedTaskScheduler.hadoop;

import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.conf.Configuration;
import com.phospher.DistributedTaskScheduler.configurations.ConfigurationPropertyName;
import org.apache.hadoop.io.Text;
import com.phospher.DistributedTaskScheduler.configurations.Task;
import com.phospher.DistributedTaskScheduler.ioc.*;
import java.io.*;

public class TaskMapper extends TaskMapReduceBase implements Mapper<Text, Task, Text, TaskRunningResult> {

	public void map(Text key, Task value, OutputCollector<Text, TaskRunningResult> output, Reporter reporter) throws IOException {
		TaskResult childTasksResult = TaskResult.SUCCESS;

		//if the task contains child tasks, executing the child tasks first
		if(value.getTasks() != null && value.getTasks().length > 0) {
			childTasksResult = this.runChildTask();
			if(childTasksResult == TaskResult.FAILURE) {
				TaskRunningResult result = new TaskRunningResult();
				result.setResult(TaskResult.FAILURE);
				result.setMessage("One or more child tasks is FAILURE");
				result.setException(new Exception("One or more child tasks is FAILURE"));
				output.collect(key, result);
				return;
			}
		}
	}

	private TaskResult getJobResult(String jobName) {
		BufferedReader reader = null;
		InputStream in = null;
		TaskResult result = TaskResult.FAILURE;
		try {
			ObjectProvider objectProvider = ObjectProviderFactory.getObjectProvider(this.getJob());
			HadoopStreamAdapter streamAdapter = (HadoopStreamAdapter)objectProvider.getInstance(HadoopStreamAdapter.class);
			in = streamAdapter.getInputStream(this.getJob(), ConfigurationPropertyName.TASK_JOB_RESULT_FILE.getPropertyName());
			reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			while(line != null) {
				String[] data = line.split(",");
				if(data.length > 1 && !data[0].isEmpty() && !data[1].isEmpty() && data[0].equals(jobName)) {
					result = Enum.valueOf(TaskResult.class, data[1]);
				}
				line = reader.readLine();
			}
		} catch(Exception ex) {
			result = TaskResult.FAILURE;
		}
		finally {
			try {
				if(reader != null) {
					reader.close();
				}
				if(in != null) {
					in.close();
				}
			} catch(Exception ex) {
				result = TaskResult.FAILURE;
			}
		}
		return result;
	}

	private TaskResult runChildTask() {
		JobConf conf = new JobConf();
		String taskCode = this.getJob().get(ConfigurationPropertyName.CURRENT_RULE_PROPERTY.getPropertyName());
		String jobName = this.getJob().get(ConfigurationPropertyName.CURRENT_JOB_ID.getPropertyName()) + "_TASK_" + taskCode;
		conf.setJobName(jobName);
		conf.set(ConfigurationPropertyName.CURRENT_RULE_PROPERTY.getPropertyName(), taskCode);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(TaskRunningResult.class);
		conf.setMapperClass(TaskMapper.class);
		conf.setReducerClass(TaskReducer.class);
		conf.setInputFormat(TaskInputFormat.class);

		try {
			RunningJob runningJob = JobClient.runJob(conf);
			runningJob.waitForCompletion();
			return this.getJobResult(jobName);
		} catch(IOException ex) {
			return TaskResult.FAILURE;
		}
	}

}