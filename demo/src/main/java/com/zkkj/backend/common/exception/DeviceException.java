package com.zkkj.backend.common.exception;

public class DeviceException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6376075283832656136L;
	
	private String exceptionCode;
	
	 public DeviceException() {
	        super();
	    }
	 
    public DeviceException(String message) {
        super(message);
    } 
    
    public DeviceException(String message,String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    } 
    
    public DeviceException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DeviceException(String message,String exceptionCode, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }
    
    public DeviceException(Throwable cause) {
        super(cause);
    }
    
    protected DeviceException(String message, Throwable cause,
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
