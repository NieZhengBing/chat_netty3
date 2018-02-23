package com.nzb.netty3.common.core.exception;

public class ErrorCodeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2656853595064684533L;

	private final int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public ErrorCodeException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

}
