/**
 * 
 */
package com.webmethods.caf.startloanprocess;

/**
 * @author oleksiyo
 *
 */

import javax.portlet.PortletPreferences;

public class StartLoanProcess  extends   com.webmethods.caf.faces.bean.BaseFacesPreferencesBean {

	public static final String[] PREFERENCES_NAMES = new String[] {};
	private com.webmethods.caf.FormFlowProcessTasks formFlowProcessTasks = null;
	
	/**
	 * Create new preferences bean with list of preference names
	 */
	public StartLoanProcess() {
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
}