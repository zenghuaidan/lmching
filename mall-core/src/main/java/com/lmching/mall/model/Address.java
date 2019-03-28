package com.lmching.mall.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.lmching.mall.model.assist.Pojo;

@Table(name = "mall_address")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Address extends Pojo {

	private static final long serialVersionUID = 7482818683522267764L;

	private String name;
	
	private String address;
	
	private String city;
	
	private String country;
	
    private String postcode;
	
	private String phone;
	
	private boolean def = false;
	
	private boolean active = false;
	
	private Long userId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public boolean isDef() {
		return def;
	}

	public void setDef(boolean def) {
		this.def = def;
	}

	@Column(nullable=true)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(nullable=false)
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Column(nullable=false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}

