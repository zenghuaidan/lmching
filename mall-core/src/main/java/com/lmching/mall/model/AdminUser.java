package com.lmching.mall.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lmching.mall.model.assist.Pojo;


@Table(name = "mall_admin_user")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AdminUser extends Pojo {
	
	private static final long serialVersionUID = 6256268382720261013L;

	@NotBlank
	private String name;	
	
	@Email
	private String email;
	
	@Length(min=6)
	private String password;
	
	private String resetPasswordCode;	
	
	private boolean active = false;

	@JsonIgnore
	@Column(nullable=true)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable=true, unique=true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable=false)
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getResetPasswordCode() {
		return resetPasswordCode;
	}

	public void setResetPasswordCode(String resetPasswordCode) {
		this.resetPasswordCode = resetPasswordCode;
	}
	
}

