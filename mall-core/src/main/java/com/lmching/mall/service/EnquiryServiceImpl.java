package com.lmching.mall.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.lmching.mall.model.Enquiry;
import com.lmching.mall.repository.EnquiryRepository;


@Service
@Transactional
public class EnquiryServiceImpl implements EnquiryService {

    @Autowired
    private EnquiryRepository enquiryRepository;  
	
    @Override
    public Enquiry save(Enquiry enquiry) {
    	return enquiryRepository.save(enquiry);
    }
    
    @Override
	public Iterable<Enquiry> findAll() {
		return enquiryRepository.findAll(new Sort(new Order(Direction.ASC, "id")));
	}

}