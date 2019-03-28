package com.lmching.mall.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.lmching.mall.model.assist.PaymentType;
import com.lmching.mall.model.assist.Pojo;

@Table(name = "mall_payment")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Payment extends Pojo {

	private static final long serialVersionUID = 7053917390394018909L;

	private Long userId;

    private Long orderId;

    private String platformNumber;

    private String platformStatus;
    
    private PaymentType type;

    @Column(nullable=false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(nullable=false)
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getPlatformNumber() {
		return platformNumber;
	}

	public void setPlatformNumber(String platformNumber) {
		this.platformNumber = platformNumber;
	}

	public String getPlatformStatus() {
		return platformStatus;
	}

	public void setPlatformStatus(String platformStatus) {
		this.platformStatus = platformStatus;
	}

	@Enumerated(EnumType.STRING)
	public PaymentType getType() {
		return type;
	}

	public void setType(PaymentType type) {
		this.type = type;
	}


}