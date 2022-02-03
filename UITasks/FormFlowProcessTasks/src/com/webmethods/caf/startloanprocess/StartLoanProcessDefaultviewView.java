/**
 * 
 */
package com.webmethods.caf.startloanprocess;

import com.webmethods.portal.mech.task.impl.TaskHelper;
import com.webmethods.portal.service.task.ITaskFormFlowService;
import com.webmethods.portal.service.task.TaskFlowObject;
import com.webmethods.portal.system.PortalSystem;
import com.webmethods.rtl.util.Debug;

/**
 * @author oleksiyo
 *
 */

public class StartLoanProcessDefaultviewView  extends   com.webmethods.caf.faces.bean.BasePageBean {


	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version
	 * of this class is not compatible with old versions. See Sun docs
	 * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization/spec/version.html> 
	 * details. </a>
	 */
	private static final long serialVersionUID = 1L;
	private static final String[][] INITIALIZE_PROPERTY_BINDINGS = new String[][] {
	};
	private com.webmethods.caf.startloanprocess.StartLoanProcess startLoanProcess = null;
	private com.webmethods.caf.is.wsclient.svc.startloanprocess.StartLoanProcess2 startLoanProcess2 = null;
	private static final String[][] STARTLOANPROCESS2_PROPERTY_BINDINGS = new String[][] {
		{"#{StartLoanProcess2.authCredentials.authenticationMethod}", "1"},
		{"#{StartLoanProcess2.authCredentials.requiresAuth}", "true"},
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

	public com.webmethods.caf.startloanprocess.StartLoanProcess getStartLoanProcess()  {
		if (startLoanProcess == null) {
		    startLoanProcess = (com.webmethods.caf.startloanprocess.StartLoanProcess)resolveExpression("#{StartLoanProcess}");
		}
		return startLoanProcess;
	}

	public com.webmethods.caf.is.wsclient.svc.startloanprocess.StartLoanProcess2 getStartLoanProcess2()  {
		if (startLoanProcess2 == null) {
		    startLoanProcess2 = (com.webmethods.caf.is.wsclient.svc.startloanprocess.StartLoanProcess2)resolveExpression("#{StartLoanProcess2}");
		}
	
	    resolveDataBinding(STARTLOANPROCESS2_PROPERTY_BINDINGS, startLoanProcess2, "startLoanProcess2", false, false);
		return startLoanProcess2;
	}
	
	public void startLoanProcess() throws Exception {
		int timeout = getTimeout();
		
		String loanNumber = getStartLoanProcess2().getParameters().getLoanNumber();
		
		TaskFlowObject waitObject = ITaskFormFlowService.Instance.get().waitPrepare(loanNumber);
		Debug.info("startLoanProcess: correlationID=" + loanNumber, TaskHelper.getLogCategory());

		// start process
		getStartLoanProcess2().refresh();
		// wait for task to be queued
		ITaskFormFlowService.Instance.get().wait(waitObject, timeout *1000);
		// check result and open task
		if (waitObject.isNotified()) {
			String taskUrl = (String) waitObject.getResult();
			Debug.info("  startLoanProcess: rediring to task: " + taskUrl,TaskHelper.getLogCategory());
			getFacesContext().getExternalContext().redirect(taskUrl);
		}
		else {
			// timeout
			throw new Exception( "The Step1 task in the flow was not created within " + timeout + " seconds");
		}
	}
	
	public String getTaskID(String taskUrl) {
		String[] r = taskUrl.split("=");
		return r[r.length - 1];
	}
	
	protected int getTimeout() {
		String timeOutString = PortalSystem.getSystemProperty("formFlowTimeoutSecs", "10");
   		return Integer.parseInt(timeOutString);
	}
}