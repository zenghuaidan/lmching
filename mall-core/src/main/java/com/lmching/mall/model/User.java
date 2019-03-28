package com.lmching.mall.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lmching.mall.model.assist.Pojo;
import com.lmching.mall.model.assist.UserType;


@Table(name = "mall_user")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User extends Pojo {
	
	private static final long serialVersionUID = 3316832358771468651L;

	@NotBlank
	private String name;	
	
	@Email
	private String email;
	
	@Length(min=6)
	private String password;
	
	@Length(min=6)
	private String verifyPassword;
	
	private String phone;
	
	@NotNull
	private UserType type;	    
	
	private int numFail;
		
	private Date lastFailTime;
		
	private String activeCode;
	
	private String resetPasswordCode;	
	
	private boolean active = false;
	
//	private List<Address> addresses;

	@JsonIgnore
	@Column(nullable=true)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
//	今天做项目的时候想在一个实体类中添加一个属性，不去关联实体类对应的表的任何字段。
//	但是发现按照网上的怎么做都不成功，最后找到了原因。
//	@Transient 在 import的时候要注意引用的是 javax.persistence这个包下的
//	而不是其他的包。我之前一直引用的SpringFramework包的那个注解。
//	而且还需要注意的是这个注解要加在属性的get方法上。
	@Transient
	public String getVerifyPassword() {
		return verifyPassword;
	}

	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
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

	@Column(nullable=true)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	@Column(nullable=false)
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Column(nullable=false)
	public int getNumFail() {
		return numFail;
	}

	public void setNumFail(int numFail) {
		this.numFail = numFail;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastFailTime() {
		return lastFailTime;
	}

	public void setLastFailTime(Date lastFailTime) {
		this.lastFailTime = lastFailTime;
	}

//	@OneToMany(mappedBy="user")
//	public List<Address> getAddresses() {
//		return addresses;
//	}
//
//	public void setAddresses(List<Address> addresses) {
//		this.addresses = addresses;
//	}
	
	@Transient
	public boolean isLock() {		
		return numFail >= 10;
	}
	
	@Transient
	public boolean isAdmin() {		
		return UserType.Admin.equals(this.type);
	}
	
	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getResetPasswordCode() {
		return resetPasswordCode;
	}

	public void setResetPasswordCode(String resetPasswordCode) {
		this.resetPasswordCode = resetPasswordCode;
	}	

	public static boolean isValidPassowd(String password) {
		boolean validLength = password.length() >= 6;
		boolean hasLetter = false;
		boolean hasNumber = false;
		for(char p : password.toLowerCase().toCharArray()) {			
			if (p >= 'a' && p <= 'z') {
				hasLetter = true;
			}
			if (p >= '0' && p <= '9') {
				hasNumber = true;
			}
		}
		return validLength && hasLetter && hasNumber;
	}
	
}

