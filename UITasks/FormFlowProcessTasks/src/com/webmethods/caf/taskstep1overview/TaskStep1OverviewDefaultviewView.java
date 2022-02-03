/**
 * 
 */
package com.webmethods.caf.taskstep1overview;

/**
 * @author oleksiyo
 *
 */

import javax.faces.application.FacesMessage;
import com.webmethods.caf.faces.data.task.TaskDisplayProvider;

public class TaskStep1OverviewDefaultviewView extends com.webmethods.caf.faces.bean.BasePageBean {


	private static final long serialVersionUID = 1L;
	private static final String[][] INITIALIZE_PROPERTY_BINDINGS = new String[][] {
		{"#{activePreferencesBean.currentTab}", "TaskData"},
	};

	// binding the Task Display Provider to the current taskID (passed to the Portlet Bean via the URL)
	private TaskDisplayProvider taskDisplayProvider = null;
	private static final String[][] TASKDISPLAYPROVIDER_PROPERTY_BINDINGS = new String[][] { {
			"#{TaskDisplayProvider.taskID}", "#{TaskStep1Overview.taskID}" }, };
	private com.webmethods.caf.taskstep1overview.TaskStep1Overview taskStep1Overview = null;
	private com.webmethods.caf.taskclient.TaskStep1 taskStep1 = null;
	private static final String[][] TASKSTEP1_PROPERTY_BINDINGS = new String[][] {
		{"#{TaskStep1.taskID}", "#{TaskStep1Overview.taskID}"},
	};

	/**
	 * Initialize page
	 */
	public String initialize() {
		try {
		    resolveDataBinding(INITIALIZE_PROPERTY_BINDINGS, null, "initialize", true, false);

			return OUTCOME_OK;
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR; 
		}	
	}

	/*
	 * Get the Task Display Provider for the current taskID
	 */
	public TaskDisplayProvider getTaskDisplayProvider() {
		if (taskDisplayProvider == null) {
			taskDisplayProvider = (TaskDisplayProvider) resolveExpression("#{TaskDisplayProvider}");
		}
		resolveDataBinding(TASKDISPLAYPROVIDER_PROPERTY_BINDINGS,
				taskDisplayProvider, "taskDisplayProvider", false, false);
		return taskDisplayProvider;
	}

	public com.webmethods.caf.taskstep1overview.TaskStep1Overview getTaskStep1Overview()  {
		if (taskStep1Overview == null) {
		    taskStep1Overview = (com.webmethods.caf.taskstep1overview.TaskStep1Overview)resolveExpression("#{TaskStep1Overview}");
		}
		return taskStep1Overview;
	}

	public com.webmethods.caf.taskclient.TaskStep1 getTaskStep1()  {
		if (taskStep1 == null) {
		    taskStep1 = (com.webmethods.caf.taskclient.TaskStep1)resolveExpression("#{TaskStep1}");
		}
	
	    resolveDataBinding(TASKSTEP1_PROPERTY_BINDINGS, taskStep1, "taskStep1", false, false);
		return taskStep1;
	}

}