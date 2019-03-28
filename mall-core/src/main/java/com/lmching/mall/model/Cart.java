package com.lmching.mall.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.lmching.mall.model.assist.Pojo;

@Table(name = "mall_cart")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cart extends Pojo {

	private static final long serialVersionUID = -3128794581306606031L;

	private Long userId;

	private Long productId;

	private int quantity;

	private boolean checked;

	@Column(nullable=false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(nullable=false)
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}