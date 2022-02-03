package com.webmethods.caf.taskclient;

import com.webmethods.portal.service.task.ITaskFormFlowService;

public class TaskStep1RuleContext  extends  com.webmethods.caf.faces.data.task.impl.BaseTaskRuleContext {
	public void queued() throws Exception {
		long t = System.currentTimeMillis();
		TaskStep1 task1 = (TaskStep1) resolveExpression("#{currentTask}");
		String loanNumber = task1.getTaskData().getLoan().getLoanNumber();
		String taskUrl = task1.getTaskInfo().getTaskURL();
		ITaskFormFlowService.Instance.get().notify(loanNumber, taskUrl, false);
		System.out.println("Task1 queue handler completed in: " + (System.currentTimeMillis() - t));
	}
}