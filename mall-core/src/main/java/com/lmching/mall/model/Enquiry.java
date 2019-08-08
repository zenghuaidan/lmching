package com.lmching.mall.model;


import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.lmching.mall.model.assist.Pojo;

@Table(name = "mall_enquiry")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Enquiry extends Pojo {

	private static final long serialVersionUID = -2489859179868484578L;			
	
	private String email;
	
	private String message;

	private boolean readed;
	
	private boolean replied;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Lob
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	public boolean isReplied() {
		return replied;
	}

	public void setReplied(boolean replied) {
		this.replied = replied;
	}
	
}

