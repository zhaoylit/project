package com.zkkj.backend.common.exception;

public class ImportkException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 883305447609363704L;
	private String exceptionCode;
	
	 public ImportkException() {
	        super();
	    }
	 
    public ImportkException(String message) {
        super(message);
    } 
    
    public ImportkException(String message,String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    } 
    
    public ImportkException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ImportkException(String message,String exceptionCode, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }
    
    public ImportkException(Throwable cause) {
        super(cause);
    }
    
    protected ImportkException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
     
    
}
