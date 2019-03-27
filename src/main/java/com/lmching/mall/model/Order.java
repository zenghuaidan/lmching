package com.lmching.mall.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Table;

import com.lmching.mall.model.assist.Pojo;

@Table(name = "Cart")
public class Order extends Pojo {

	private static final long serialVersionUID = 6286945117715112467L;


    private Long orderNo;

    private Integer userId;

    private Integer shippingId;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private Date paymentTime;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;


}