/**
 * 
 */
package com.webmethods.caf.taskstep2view;

/**
 * @author oleksiyo
 *
 */

import java.util.ArrayList;
import java.util.List;

import com.webmethods.caf.faces.context.ContextUtils;
import com.webmethods.caf.faces.data.dir.PrincipalModel;
import com.webmethods.caf.faces.data.dir.PrincipalModelList;
import com.webmethods.caf.faces.data.task.impl.TaskContentProvider;
import com.webmethods.portal.mech.task.impl.TaskHelper;
import com.webmethods.portal.service.task.ITaskFormFlowService;
import com.webmethods.portal.service.task.TaskFlowObject;
import com.webmethods.portal.system.PortalSystem;
import com.webmethods.rtl.util.Debug;

public class TaskStep2ViewDefaultviewView extends com.webmethods.caf.faces.bean.task.BaseTaskDetailsPageBean {

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
			String url = getTaskStep2View().getFinishUrl();
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
			String loanNumber = getTaskStep2().getTaskData().getLoan().getLoanNumber();
			TaskFlowObject waitObject = ITaskFormFlowService.Instance.get().waitPrepare(loanNumber);
			
			// complete task to let process to transition to next step
			getTaskStep2().completeTask();
			Debug.info("TaskStep2: completed task " + getTaskStep2().getTaskID() + ", correlationID=" + loanNumber, TaskHelper.getLogCategory());

			int timeout = getTimeout();

			// This is last task in the flow, check if the process notified us that it is complete
			ITaskFormFlowService.Instance.get().wait(waitObject, timeout *1000);
			
			if (waitObject.isNotified()) {
				// we received a process notification
				String result = (String) waitObject.getResult();
				Debug.info("TaskStep2: redirecting to /start.loan.process, correlationID=" + loanNumber, TaskHelper.getLogCategory());
				if( "COMPLETE".equals(result)) {
					getFacesContext().getExternalContext().redirect("/start.loan.process");
				}
				else {
					throw new Exception( "Expected process result COMPLETE, but instead received: " + result +", correlationID=" + loanNumber );
				}
			}
			else {
				ContextUtils.error( "TaskStep2: the process did not complete within " + timeout + " seconds, correctionID=" + loanNumber);
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
			if( !getTaskStep2().isUpdateable() ){
				String errMsg = "You must accept a task before updating it";	//view.task.pagebean.task.accept.msg
				error(errMsg);
				return OUTCOME_ERROR; 
			}


			// do the work
			getTaskStep2().applyChanges();

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
			TaskContentProvider tp = getTaskStep2();

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
		{"#{TaskStep2.reset}", null}
	};
	private com.webmethods.caf.taskstep2view.TaskStep2View taskStep2View = null;
	private com.webmethods.caf.taskclient.TaskStep2 taskStep2 = null;
	private static final String[][] TASKSTEP2_PROPERTY_BINDINGS = new String[][] {
		{"#{taskStep2.taskID}", "#{TaskStep2View.taskID}"},
		{"#{taskStep2.autoAccept}", "false"},
		{"#{taskStep2.adhocRouting}", "false"},
		{"#{taskStep2.needAcceptToUpdate}", "false"},
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

	public com.webmethods.caf.taskstep2view.TaskStep2View getTaskStep2View()  {
		if (taskStep2View == null) {
		    taskStep2View = (com.webmethods.caf.taskstep2view.TaskStep2View)resolveExpression("#{TaskStep2View}");
		}
		return taskStep2View;
	}

	public com.webmethods.caf.taskclient.TaskStep2 getTaskStep2()  {
		if (taskStep2 == null) {
		    taskStep2 = (com.webmethods.caf.taskclient.TaskStep2)resolveExpression("#{TaskStep2}");
		}
	
	    resolveDataBinding(TASKSTEP2_PROPERTY_BINDINGS, taskStep2, "taskStep2", false, false);
		return taskStep2;
	}

	protected int getTimeout() {
		String timeOutString = PortalSystem.getSystemProperty("formFlowTimeoutSecs", "10");
   		return Integer.parseInt(timeOutString);
	}

	
}