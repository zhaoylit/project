package com.zkkj.backend.common.exception;

public class FileException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5366356461332765502L;
	
	private String exceptionCode;
	
	 public FileException() {
	        super();
	    }
	 
    public FileException(String message) {
        super(message);
    } 
    
    public FileException(String message,String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    } 
    
    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public FileException(String message,String exceptionCode, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }
    
    public FileException(Throwable cause) {
        super(cause);
    }
    
    protected FileException(String message, Throwable cause,
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
