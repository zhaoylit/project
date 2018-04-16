package com.zkkj.backend.common.exception;

public class ApkException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4664723190424820052L;
	/**
	 * 
	 */
	
	private String exceptionCode;
	
	 public ApkException() {
	        super();
	    }
	 
    public ApkException(String message) {
        super(message);
    } 
    
    public ApkException(String message,String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    } 
    
    public ApkException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ApkException(String message,String exceptionCode, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }
    
    public ApkException(Throwable cause) {
        super(cause);
    }
    
    protected ApkException(String message, Throwable cause,
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
