package com.zkkj.backend.common.exception;

public class NoticeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1294969425421108271L;
	private String exceptionCode;
	
	 public NoticeException() {
	        super();
	    }
	 
    public NoticeException(String message) {
        super(message);
    } 
    
    public NoticeException(String message,String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    } 
    
    public NoticeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public NoticeException(String message,String exceptionCode, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }
    
    public NoticeException(Throwable cause) {
        super(cause);
    }
    
    protected NoticeException(String message, Throwable cause,
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
