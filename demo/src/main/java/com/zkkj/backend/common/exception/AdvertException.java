package com.zkkj.backend.common.exception;

public class AdvertException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2138766185629046330L;
	
	private String exceptionCode;
	
	 public AdvertException() {
	        super();
	    }
	 
    public AdvertException(String message) {
        super(message);
    } 
    
    public AdvertException(String message,String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    } 
    
    public AdvertException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public AdvertException(String message,String exceptionCode, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }
    
    public AdvertException(Throwable cause) {
        super(cause);
    }
    
    protected AdvertException(String message, Throwable cause,
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
