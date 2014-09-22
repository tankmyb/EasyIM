package com.imbase.Enum;

public enum LogTypeEnum {

	ORDER("order");
	private String value;
	LogTypeEnum(String value){
		this.value =  value;
	}
	public String getValue(){
		return this.value;
	}
}
