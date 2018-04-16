package com.zkkj.backend.common.exception;

public class DataBaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2116994034724715561L;
	private String exceptionCode;
	
	 public DataBaseException() {
	        super();
	    }
	 
    public DataBaseException(String message) {
        super(message);
    } 
    
    public DataBaseException(String message,String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    } 
    
    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DataBaseException(String message,String exceptionCode, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }
    
    public DataBaseException(Throwable cause) {
        super(cause);
    }
    
    protected DataBaseException(String message, Throwable cause,
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
