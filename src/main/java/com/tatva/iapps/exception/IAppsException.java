package com.tatva.iapps.exception;

import java.io.Serializable;

public class IAppsException extends Exception implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public IAppsException(){
		super();
	}
	public IAppsException(String messgae){
		super(messgae);
	}
	
}
