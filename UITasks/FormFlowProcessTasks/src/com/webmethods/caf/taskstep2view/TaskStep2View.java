/**
 * 
 */
package com.webmethods.caf.taskstep2view;

/**
 * @author oleksiyo
 *
 */

import javax.portlet.PortletPreferences;

public class TaskStep2View  extends   com.webmethods.caf.faces.bean.BaseFacesPreferencesBean {

	private com.webmethods.caf.FormFlowProcessTasks formFlowProcessTasks = null;
	/**
	 * List of portlet preference names
	 */
	public static final String[] PREFERENCES_NAMES = new String[] {
		"taskID", "cancelUrl", "finishUrl"
	};
	/**
	 * Create new preferences bean with list of preference names
	 */
	public TaskStep2View() {
		super(PREFERENCES_NAMES);
	}
	
	/**
	 * Call this method in order to persist
	 * Portlet preferences
	 */
	public void storePreferences() throws Exception {
		updatePreferences();
		PortletPreferences preferences = getPreferences();
		preferences.store();
	}

	public com.webmethods.caf.FormFlowProcessTasks getFormFlowProcessTasks()  {
		if (formFlowProcessTasks == null) {
		    formFlowProcessTasks = (com.webmethods.caf.FormFlowProcessTasks)resolveExpression("#{FormFlowProcessTasks}");
		}
		return formFlowProcessTasks;
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String getTaskID() throws Exception {
		return (String) getPreferenceValue("taskID", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setTaskID(String taskID) throws Exception {
		setPreferenceValue("taskID", taskID);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String getCancelUrl() throws Exception {
		return (String) getPreferenceValue("cancelUrl", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setCancelUrl(String cancelUrl) throws Exception {
		setPreferenceValue("cancelUrl", cancelUrl);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String getFinishUrl() throws Exception {
		return (String) getPreferenceValue("finishUrl", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setFinishUrl(String finishUrl) throws Exception {
		setPreferenceValue("finishUrl", finishUrl);
	}
}