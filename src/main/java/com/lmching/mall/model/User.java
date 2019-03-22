package com.lmching.mall.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "User", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User implements Serializable {
	
	private static final long serialVersionUID = -5051085506032468813L;
	private Long id;	
	
	@NotBlank
	private String name;	
	
	@Email
	@NotBlank
	private String email;
	
	@Length(min=6)
	private String password;
	
	@Length(min=6)
	private String verifyPassword;
	
	@NotBlank
	private String phone;
	
	@NotNull
	private UserType type;
	
    private AuthProvider provider;
    
    private String providerId;
	
	private int numFail;
		
	private Date lastFailTime;
		
	private String activeCode;
	
	private String resetPasswordCode;	
	
	private boolean active = false;
	
	private Date createTime = new Date();
	private Date updateTime;
	
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY) 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	@Column(nullable=false)
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

	@Column(nullable=false, unique=true)
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
	
    @NotNull
    @Enumerated(EnumType.STRING)
	public AuthProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
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
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastFailTime() {
		return lastFailTime;
	}

	public void setLastFailTime(Date lastFailTime) {
		this.lastFailTime = lastFailTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	// The column type in mysql will be DATETIME, should use columnDefinition to limit it to TIMESTAMP to make it auto updatable
	@Column(nullable=false, insertable = false, updatable = false, columnDefinition="TIMESTAMP")  
	@Generated(GenerationTime.ALWAYS)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}	
	
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

