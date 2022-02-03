/**
 * 
 */
package com.webmethods.caf.taskstep1view;

/**
 * @author oleksiyo
 *
 */

import java.util.ArrayList;
import java.util.List;

import com.webmethods.caf.faces.data.dir.PrincipalModel;
import com.webmethods.caf.faces.data.dir.PrincipalModelList;
import com.webmethods.caf.faces.data.task.impl.TaskContentProvider;
import com.webmethods.portal.mech.task.impl.TaskHelper;
import com.webmethods.portal.service.task.ITaskFormFlowService;
import com.webmethods.portal.service.task.TaskFlowObject;
import com.webmethods.portal.system.PortalSystem;
import com.webmethods.rtl.util.Debug;

public class TaskStep1ViewDefaultviewView extends com.webmethods.caf.faces.bean.task.BaseTaskDetailsPageBean {

	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version
	 * of this class is not compatible with old versions. See Sun docs
	 * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization/spec/version.html> 
	 * details. </a>
	 */
	private static final long serialVersionUID = 1L;
	private com.webmethods.caf.faces.data.task.TaskDisplayProvider taskDisplayProvider = null;
	private static final String[][] TASKDISPLAYPROVIDER_PROPERTY_BINDINGS = new String[][] {
		{"#{TaskDisplayProvider.taskID}", "#{activePreferencesBean.taskID}"},
	};

	public com.webmethods.caf.faces.data.task.TaskDisplayProvider getTaskDisplayProvider()  {
		if (taskDisplayProvider == null) {
		    taskDisplayProvider = (com.webmethods.caf.faces.data.task.TaskDisplayProvider)resolveExpression("#{TaskDisplayProvider}");
		}
	    resolveDataBinding(TASKDISPLAYPROVIDER_PROPERTY_BINDINGS, taskDisplayProvider, "taskDisplayProvider", false, false);
		return taskDisplayProvider;
	}

	/**
	 * Cancel button action handler
	 */
	public String cancelView() {
		try {
			// just redirect to return (finish) url
			String url = getTaskStep1View().getFinishUrl();
			if (url != null && url.length() > 0) {
				getFacesContext().getExternalContext().redirect(url);
			}
			return OUTCOME_OK;
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR; 
		}
	}
	
	/**
	 * Complete button action handler
	 */
	public String completeTask() {
		try {
			// setup wait
			String loanNumber = getTaskStep1().getTaskData().getLoan().getLoanNumber();
			TaskFlowObject waitObject = ITaskFormFlowService.Instance.get().waitPrepare(loanNumber);
			
			
			// complete task to let process to transition to next step
			getTaskStep1().completeTask();
			Debug.info("TaskStep1: completed task " + getTaskStep1().getTaskID() + ", correlationID=" + loanNumber, TaskHelper.getLogCategory());

			int timeout = getTimeout();

			// wait for next task in the process
			ITaskFormFlowService.Instance.get().wait(waitObject, timeout *1000);
			
			// check result and goto next task
			if (waitObject.isNotified()) {
				String taskUrl = (String) waitObject.getResult();
				Debug.info("TaskStep1: redirecting to " + taskUrl + ", correlationID=" + loanNumber, TaskHelper.getLogCategory());

				getFacesContext().getExternalContext().redirect(taskUrl);
			}
			else {
				throw new Exception( "The Step2 task in the flow was not created within " + timeout + ", correlationID=" + loanNumber );
			}

			return OUTCOME_OK;
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR; 
		}
	}	

	/**
	 * Submit button action handler
	 */
	public String submitTask() {
		try {
			if( !getTaskStep1().isUpdateable() ){
				String errMsg = "You must accept a task before updating it";	//view.task.pagebean.task.accept.msg
				error(errMsg);
				return OUTCOME_ERROR; 
			}


			// do the work
			getTaskStep1().applyChanges();

			return OUTCOME_OK;
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR; 
		}
	}

	private PrincipalModelList selectedPrincipalList;

	public PrincipalModelList getPrincipalList() {
		return selectedPrincipalList;
	}
	
	public void setPrincipalList(PrincipalModelList value) {
		this.selectedPrincipalList = value;
	}
	
	/**
	 * Action Event Handler for the control with id='dialogPrincipalAssignTo'
	 */
	public String assignToPrincipals() {
		try {
			
			// get the current assigned principals for this task
			TaskContentProvider tp = getTaskStep1();

			List<String> currentPrincipalList = new ArrayList<String>();
			String[] currentPrincipalIDs = tp.getTaskInfo().getAssignedToList();
			if (currentPrincipalIDs != null && currentPrincipalIDs.length > 0) {
				for (int ix = 0; ix < currentPrincipalIDs.length; ix++) {
					String principalID = currentPrincipalIDs[ix];
					currentPrincipalList.add( principalID );
				}
			}
			
			// get the selected principals from the picker
			if (selectedPrincipalList != null && selectedPrincipalList.size() > 0) {
				// loop and add the selected principals to the existing list
				for (int ix = 0; ix < selectedPrincipalList.size(); ix++) {
					PrincipalModel principalModel = (PrincipalModel) selectedPrincipalList.get(ix);
					String principalID = principalModel.getPrincipalID(); 
					if( !currentPrincipalList.contains( principalID)) {
						currentPrincipalList.add( principalID );
					}
				}
			}
				
			currentPrincipalIDs = currentPrincipalList.toArray( currentPrincipalIDs);
			tp.getTaskInfo().setAssignedToList(currentPrincipalIDs);
			tp.applyChangesNoAccept();
				
			// then redirect to finish url
			//String url = getRonTask36TaskView().getFinishUrl(); 
			//if (url != null && url.length() > 0) {
			//	getFacesContext().getExternalContext().redirect(url);
			//}				
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR;
		}		
		return OUTCOME_OK;
	}
	
	private static final String[][] INITIALIZE_PROPERTY_BINDINGS = new String[][] {
		{"#{TaskStep1.reset}", null}
	};
	private com.webmethods.caf.taskstep1view.TaskStep1View taskStep1View = null;
	private com.webmethods.caf.taskclient.TaskStep1 taskStep1 = null;
	private static final String[][] TASKSTEP1_PROPERTY_BINDINGS = new String[][] {
		{"#{taskStep1.taskID}", "#{TaskStep1View.taskID}"},
		{"#{taskStep1.autoAccept}", "false"},
		{"#{taskStep1.adhocRouting}", "false"},
		{"#{taskStep1.needAcceptToUpdate}", "false"},
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
	
	@Override
	protected void beforeRenderResponse() {
		super.beforeRenderResponse();
	}

	public com.webmethods.caf.taskstep1view.TaskStep1View getTaskStep1View()  {
		if (taskStep1View == null) {
		    taskStep1View = (com.webmethods.caf.taskstep1view.TaskStep1View)resolveExpression("#{TaskStep1View}");
		}
		return taskStep1View;
	}

	public com.webmethods.caf.taskclient.TaskStep1 getTaskStep1()  {
		if (taskStep1 == null) {
		    taskStep1 = (com.webmethods.caf.taskclient.TaskStep1)resolveExpression("#{TaskStep1}");
		}
	
	    resolveDataBinding(TASKSTEP1_PROPERTY_BINDINGS, taskStep1, "taskStep1", false, false);
		return taskStep1;
	}
	
	protected int getTimeout() {
		String timeOutString = PortalSystem.getSystemProperty("formFlowTimeoutSecs", "10");
   		return Integer.parseInt(timeOutString);
	}
	
}