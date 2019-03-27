package com.lmching.mall.model.assist;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;


@Entity
public class Pojo implements Serializable {
	
	private Long id;	
	
	
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	
}

