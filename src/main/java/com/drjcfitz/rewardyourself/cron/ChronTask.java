package com.drjcfitz.rewardyourself.cron;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ChronTask extends QuartzJobBean {

	//private static TestClass testClass;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		//testClass.updateDb();
	}

}
