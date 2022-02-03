/**
 * 
 */
package com.webmethods.caf.taskstep2overview;

/**
 * @author oleksiyo
 *
 */

import javax.faces.application.FacesMessage;
import com.webmethods.caf.faces.data.task.TaskDisplayProvider;

public class TaskStep2OverviewDefaultviewView extends com.webmethods.caf.faces.bean.BasePageBean {


	private static final long serialVersionUID = 1L;
	private static final String[][] INITIALIZE_PROPERTY_BINDINGS = new String[][] {
		{"#{activePreferencesBean.currentTab}", "TaskData"},
	};

	// binding the Task Display Provider to the current taskID (passed to the Portlet Bean via the URL)
	private TaskDisplayProvider taskDisplayProvider = null;
	private static final String[][] TASKDISPLAYPROVIDER_PROPERTY_BINDINGS = new String[][] { {
			"#{TaskDisplayProvider.taskID}", "#{TaskStep2Overview.taskID}" }, };
	private com.webmethods.caf.taskstep2overview.TaskStep2Overview taskStep2Overview = null;
	private com.webmethods.caf.taskclient.TaskStep2 taskStep2 = null;
	private static final String[][] TASKSTEP2_PROPERTY_BINDINGS = new String[][] {
		{"#{TaskStep2.taskID}", "#{TaskStep2Overview.taskID}"},
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

	public com.webmethods.caf.taskstep2overview.TaskStep2Overview getTaskStep2Overview()  {
		if (taskStep2Overview == null) {
		    taskStep2Overview = (com.webmethods.caf.taskstep2overview.TaskStep2Overview)resolveExpression("#{TaskStep2Overview}");
		}
		return taskStep2Overview;
	}

	public com.webmethods.caf.taskclient.TaskStep2 getTaskStep2()  {
		if (taskStep2 == null) {
		    taskStep2 = (com.webmethods.caf.taskclient.TaskStep2)resolveExpression("#{TaskStep2}");
		}
	
	    resolveDataBinding(TASKSTEP2_PROPERTY_BINDINGS, taskStep2, "taskStep2", false, false);
		return taskStep2;
	}

}