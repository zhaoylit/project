package com.zkkj.backend.common.exception;

public class AuthException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8467170360276841907L;
	
	private String exceptionCode;
	
	 public AuthException() {
	        super();
	    }
	 
    public AuthException(String message) {
        super(message);
    } 
    
    public AuthException(String message,String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    } 
    
    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public AuthException(String message,String exceptionCode, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }
    
    public AuthException(Throwable cause) {
        super(cause);
    }
    
    protected AuthException(String message, Throwable cause,
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
