package com.zkkj.backend.common.exception;

public class NetworkException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6488781310832521188L;
	
	private String exceptionCode;
	
	 public NetworkException() {
	        super();
	    }
	 
    public NetworkException(String message) {
        super(message);
    } 
    
    public NetworkException(String message,String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    } 
    
    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public NetworkException(String message,String exceptionCode, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }
    
    public NetworkException(Throwable cause) {
        super(cause);
    }
    
    protected NetworkException(String message, Throwable cause,
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
