package com.lmching.mall.repository;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.lmching.mall.model.Enquiry;

public interface EnquiryRepository extends PagingAndSortingRepository<Enquiry, Long> {
	
	Iterable<Enquiry> findAll(Sort sort);
}