package com.lmching.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lmching.mall.model.Enquiry;
import com.lmching.mall.service.EnquiryService;

@Controller
@RequestMapping("/enquiry")
public class EnquiryController {
	
	@Autowired
	EnquiryService enquiryService;
	
	@GetMapping(path = {"list"})
	@ResponseBody
	public Iterable<Enquiry> list(Model model) {
		return enquiryService.findAll();
	}
	
}
