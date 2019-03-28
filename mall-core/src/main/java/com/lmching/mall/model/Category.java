package com.lmching.mall.model;


import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "Category")
public class Category implements Serializable {
	
	private static final long serialVersionUID = -5051085506032468813L;

	private Long pid;
	
	private String type;
	
	private String nameEN;	
	
	private String nameTC;
		
	private Integer order;
	
	private boolean active = true;
		
	@Column(nullable=false)
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column(nullable=false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(nullable=false)
	public String getNameEN() {
		return nameEN;
	}

	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}
	
	public String getNameTC() {
		return nameTC;
	}

	public void setNameTC(String nameTC) {
		this.nameTC = nameTC;
	}
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Column(nullable=false)
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName(Locale locale) {
		return locale.equals(Locale.CHINA) ? this.nameTC : this.nameEN;
	}
	
}

