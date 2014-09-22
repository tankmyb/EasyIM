package com.imbase.exception;

public class DisconnectException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6411729692170835995L;

	public DisconnectException(String msg){
		super(msg);
	}
	public DisconnectException(Exception e){
		super(e);
	}
}
