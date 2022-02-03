package com.webmethods.caf.is.wsclient.svc.startloanprocess;


import com.webmethods.caf.faces.data.ws.glue.GlueWSClientContentProvider;

/**
 * Glue Web Service Client bean generated for 
 * com.webmethods.caf.is.wsclient.svc.startloanprocess.IsvcPortType.startLoanProcess.
 */
public class StartLoanProcess2 extends GlueWSClientContentProvider {

	private static final long serialVersionUID = 5224419546584526848L;

	/**
	 * Default constructor
	 */
	public StartLoanProcess2() {
		super(com.webmethods.caf.is.wsclient.svc.startloanprocess.IsvcPortType.class, // port type proxy class
			"startLoanProcess", // method to invoke
			new String[] { "loanNumber", }, // method parameter names
			new Class[] { java.lang.String.class, } // method parameter types
		);
		// wsdl and maps by are saved in the same package
		setWsdlUrl("classpath:startLoanProcess.wsdl");
		setMapUrls(new String[] {});

		// init wsclient
		initParams();
		
		// parameters bean
		parameters = new Parameters();
		
	}
	
	
	/**
	 * Method parameters bean
	 */
	public class Parameters  implements java.io.Serializable {

		private static final long serialVersionUID = 5587381293361462272L;

		public Parameters() {
		}
	
		private java.lang.String loanNumber ;

		public java.lang.String getLoanNumber() {
			return loanNumber;
		}

		public void setLoanNumber(java.lang.String loanNumber) {
			this.loanNumber = loanNumber;
		} 	
		
	}
	
	
	
	/**
	 * Return method invocation parameters bean
	 */
	public Parameters getParameters() {
		return (Parameters)parameters;
	}	
	


	

		
}