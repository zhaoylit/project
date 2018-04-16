package com.zkkj.backend.common.exception;

public class ServerException extends Exception {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8556750714509920583L;
	private String exceptionCode;
	
	 public ServerException() {
	        super();
	    }
	 
    public ServerException(String message) {
        super(message);
    } 
    
    public ServerException(String message,String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    } 
    
    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ServerException(String message,String exceptionCode, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }
    
    public ServerException(Throwable cause) {
        super(cause);
    }
    
    protected ServerException(String message, Throwable cause,
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
