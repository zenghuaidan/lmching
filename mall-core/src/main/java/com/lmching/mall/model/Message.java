package com.lmching.mall.model;


import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.lmching.mall.model.assist.Pojo;

@Table(name = "mall_message")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Message extends Pojo {

	private static final long serialVersionUID = -2489859179868484578L;	
	
	private Long pid;
	
	private Long fromUserId;
	
	private Long toUserId;
	
	private String message;

	private boolean readed;	
	
	private boolean deleteOnFrom = false;
	
	private boolean deleteOnTo = false;

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Long getToUserId() {
		return toUserId;
	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
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

	public boolean isDeleteOnFrom() {
		return deleteOnFrom;
	}

	public void setDeleteOnFrom(boolean deleteOnFrom) {
		this.deleteOnFrom = deleteOnFrom;
	}

	public boolean isDeleteOnTo() {
		return deleteOnTo;
	}

	public void setDeleteOnTo(boolean deleteOnTo) {
		this.deleteOnTo = deleteOnTo;
	}
	
}

