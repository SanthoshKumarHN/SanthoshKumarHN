/**
 * 
 */
package com.webmethods.caf.taskstep1start;

/**
 * @author oleksiyo
 *
 */

import javax.portlet.PortletPreferences;

public class TaskStep1Start  extends   com.webmethods.caf.faces.bean.BaseFacesPreferencesBean {

	private com.webmethods.caf.FormFlowProcessTasks formFlowProcessTasks = null;
	/**
	 * List of portlet preference names
	 */
	public static final String[] PREFERENCES_NAMES = new String[] {
		"returnUrl"
	};
	
	/**
	 * Create new preferences bean with list of preference names
	 */
	public TaskStep1Start() {
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
	public String getReturnUrl() throws Exception {
		return (String) getPreferenceValue("returnUrl", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setReturnUrl(String returnUrl) throws Exception {
		setPreferenceValue("returnUrl", returnUrl);
	}
}