package com.imbase.exception;

public class SyncRespTimeoutException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9222610832896113260L;

	public SyncRespTimeoutException(String s){
		super(s);
	}
	public SyncRespTimeoutException(Exception e){
		super(e);
	}
}
