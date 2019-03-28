package com.lmching.mall.model;


import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.lmching.mall.model.assist.Pojo;

@Table(name = "mall_category")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Category extends Pojo {
	
	private static final long serialVersionUID = -5051085506032468813L;

	private Long pid;
	
	private String type;
	
	private String nameEN;	
	
	private String nameTC;
		
	private Integer corder;
	
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
	
	public Integer getCorder() {
		return corder;
	}

	public void setCorder(Integer corder) {
		this.corder = corder;
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

