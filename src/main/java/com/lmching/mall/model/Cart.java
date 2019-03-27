package com.lmching.mall.model;

import javax.persistence.Table;

import com.lmching.mall.model.assist.Pojo;

@Table(name = "Cart")
public class Cart extends Pojo {

	private static final long serialVersionUID = -3128794581306606031L;

	private int userId;

	private int productId;

	private int quantity;

	private boolean checked;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
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