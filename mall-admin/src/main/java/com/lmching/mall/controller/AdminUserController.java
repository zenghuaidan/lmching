package com.lmching.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lmching.mall.model.AdminUser;
import com.lmching.mall.service.AdminUserService;

@Controller
@RequestMapping("/adminuser")
public class AdminUserController {
	
	@Autowired
	AdminUserService adminUserService;
	
	@GetMapping(path = {"list"})
	@ResponseBody
	public Iterable<AdminUser> list(Model model) {
		return adminUserService.findAll();
	}
	
	@PostMapping(path = {"create", "update"})
	@ResponseBody
	public AdminUser createOrUpdate(AdminUser user) {
		return adminUserService.save(user);
	}
	
	@PostMapping(path = {"delete"})
	@ResponseBody
	public void delete(Long id) {	
		adminUserService.deleteById(id);
	}
	
	@PostMapping(path = {"findByEmail"})
	@ResponseBody
	public AdminUser findByEmail(String email) {	
		return adminUserService.findByEmail(email);
	}
	
}
