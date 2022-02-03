package com.webmethods.caf.taskclient;


import com.webmethods.caf.faces.data.task.ITaskData;
import com.webmethods.caf.faces.data.ContentProviderException;

/**
 * Task Client bean for 'Task Step 1' task.
 */
public class TaskStep1 extends com.webmethods.caf.faces.data.task.impl.TaskContentProviderExtended {

	private static final long serialVersionUID = 1545469698430113792L;
	
	/**
	 * Globally unique task type id
	 */
	private static final String TASK_TYPE_ID = "B1FCC27F-5DAA-49F9-3F70-D257C85976B6";

	/**
	 * Default constructor
	 */
	public TaskStep1() {
		super();
		setTaskTypeID(TASK_TYPE_ID);
	}
	
	/**
	 * Task Data Object
	 */
	public static class TaskData extends com.webmethods.caf.faces.data.task.impl.TaskData {

		private static final long serialVersionUID = 6799440624543929344L;
		
		public static String[][] FIELD_NAMES = new String[][] {{"loan", "Loan"},
		};

		private com.webmethods.caf.is.document.Docs_Loan loan = null;

		public static final String[] INPUTS = new String[] {"loan", };

		public static final String[] OUTPUTS = new String[] {"loan", };

		public TaskData() {
		}

		public com.webmethods.caf.is.document.Docs_Loan getLoan()  {
			if (loan == null) {
				loan = new com.webmethods.caf.is.document.Docs_Loan();
			}
			return loan;
		}

		public void setLoan(com.webmethods.caf.is.document.Docs_Loan loan)  {
			this.loan = loan;
		}
		
	}
	
	/**
	 * Return current task data object
	 * @return current task data
	 */
	public TaskData getTaskData() {
		return (TaskData)getValue(PROPERTY_KEY_TASKDATA);
	}

	/**
	 * Creates new task data object
	 * @return newly created task data object
	 */	
	protected ITaskData newTaskData() throws ContentProviderException {
		return new TaskData();
	}

}