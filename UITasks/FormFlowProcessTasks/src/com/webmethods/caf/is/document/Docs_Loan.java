package com.webmethods.caf.is.document;

import java.io.Serializable;

/**
 * IS document wrapper
 */
public  class Docs_Loan extends java.lang.Object implements Serializable {

	
	private static final long serialVersionUID = 1L;
	// IS Document type used to generate this class
	public static final String DOCUMENT_TYPE = "docs:Loan";
	private static final String DOCUMENT_SRC = "http://localhost:5555";
	public static String[][] FIELD_NAMES = new String[][] {{"loanNumber", "LoanNumber"},
	};
	private java.lang.String loanNumber;
	

	public Docs_Loan() {
	}


	public java.lang.String getLoanNumber()  {
		
		return loanNumber;
	}


	public void setLoanNumber(java.lang.String loanNumber)  {
		this.loanNumber = loanNumber;
	}

}