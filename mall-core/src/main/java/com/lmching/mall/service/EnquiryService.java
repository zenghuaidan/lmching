package com.lmching.mall.service;

import javax.transaction.Transactional;

import com.lmching.mall.model.Enquiry;


@Transactional
public interface EnquiryService {

	public Iterable<Enquiry> findAll();  

    public Enquiry save(Enquiry enquiry);

}