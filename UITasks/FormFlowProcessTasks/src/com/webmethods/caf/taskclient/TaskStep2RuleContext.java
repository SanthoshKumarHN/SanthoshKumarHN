package com.webmethods.caf.taskclient;

import com.webmethods.portal.service.task.ITaskFormFlowService;

public class TaskStep2RuleContext  extends  com.webmethods.caf.faces.data.task.impl.BaseTaskRuleContext {
	public void queued() throws Exception {
		TaskStep2 task1 = (TaskStep2) resolveExpression("#{currentTask}");
		String loanNumber = task1.getTaskData().getLoan().getLoanNumber();
		String taskUrl = task1.getTaskInfo().getTaskURL();
		ITaskFormFlowService.Instance.get().notify(loanNumber, taskUrl, false);
	}	
}