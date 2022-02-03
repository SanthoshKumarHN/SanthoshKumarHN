/**
 * 
 */
package com.webmethods.caf.taskstep1start;

/**
 * @author oleksiyo
 *
 */

import javax.faces.application.FacesMessage;
import java.text.MessageFormat;

public class TaskStep1StartDefaultviewView extends com.webmethods.caf.faces.bean.task.BaseTaskStartPageBean {


	

	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version
	 * of this class is not compatible with old versions. See Sun docs
	 * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization/spec/version.html> 
	 * details. </a>
	 */
	private static final long serialVersionUID = 1L;
	private com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider taskPriorityMapProvider = null;
	private static final String[][] TASKPRIORITYMAPPROVIDER_PROPERTY_BINDINGS = new String[][] {
		{"#{TaskPriorityMapProvider.labelFieldName}", "label"},
		{"#{TaskPriorityMapProvider.array}", "#{TaskDisplayProvider.taskPriorityItems}"},
		{"#{TaskPriorityMapProvider.valueFieldName}", "value"},
	}; 
	public com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider getTaskPriorityMapProvider()  {
		if (taskPriorityMapProvider == null) {
		    taskPriorityMapProvider = (com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider)resolveExpression("#{TaskPriorityMapProvider}");
		}
	    resolveDataBinding(TASKPRIORITYMAPPROVIDER_PROPERTY_BINDINGS, taskPriorityMapProvider, null, false, true);
		return taskPriorityMapProvider;
	}
	
	public String getPortletResource( String key ) {
	    return (String)getActivePreferencesBean().getPortletResourcesProvider().getValue( key );
	}

    /**
     * Queue new task. 
     * If successful adds INFO message to the faces context.
     * If failed adds error messages to the faces context.
     */
	public String queueNewTask() {
		try {
			// do the work
			getTaskStep1().queueNewTask();

			
			
			
				
			String msgHeader = getPortletResource("start.task.pagebean.task.start.msg");//"The new task has been successfully started"; 
			String taskIdStr = getPortletResource("start.task.pagebean.task.is.msg");
			String msgText = MessageFormat.format( taskIdStr, new Object[] {getTaskStep1().getTaskID()}); 
			getFacesContext().addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, 
				msgHeader, 
				 msgText));

			return OUTCOME_OK; 
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR; 
		}	
	}
	
   /**
	 * Go to the URL specified by the 'returnURL' preference
	 */
	public String doReturnURL() {
		try {
			// just redirect to return (finish) url
			String returnURL = (String)getTaskStep1Start().getReturnUrl();
			if (returnURL != null && returnURL.length() > 0) {
				getFacesContext().getExternalContext().redirect(returnURL);
			}
			return OUTCOME_OK;
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR; 
		}
	}
	
	private static final String[][] INITIALIZE_PROPERTY_BINDINGS = new String[][] {
	};
	private com.webmethods.caf.taskstep1start.TaskStep1Start taskStep1Start = null;
	private com.webmethods.caf.taskclient.TaskStep1 taskStep1 = null;
	private static final String[][] TASKSTEP1_PROPERTY_BINDINGS = new String[][] {
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

	public com.webmethods.caf.taskstep1start.TaskStep1Start getTaskStep1Start()  {
		if (taskStep1Start == null) {
		    taskStep1Start = (com.webmethods.caf.taskstep1start.TaskStep1Start)resolveExpression("#{TaskStep1Start}");
		}
		return taskStep1Start;
	}

	public com.webmethods.caf.taskclient.TaskStep1 getTaskStep1()  {
		if (taskStep1 == null) {
		    taskStep1 = (com.webmethods.caf.taskclient.TaskStep1)resolveExpression("#{TaskStep1}");
		}
	
	    resolveDataBinding(TASKSTEP1_PROPERTY_BINDINGS, taskStep1, "taskStep1", false, false);
		return taskStep1;
	}

}