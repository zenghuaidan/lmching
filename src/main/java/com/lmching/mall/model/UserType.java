package com.lmching.mall.model;

public enum UserType {
	Buyer("Buyer"), 
	Seller("Seller"), 
	Wholesaler("Wholesaler"), 
	Admin("Admin"); 
	
	private String code;	
	private UserType(String code) {
		this.code = code;		
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}