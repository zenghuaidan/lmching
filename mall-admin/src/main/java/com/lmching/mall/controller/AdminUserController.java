package com.lmching.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
		AdminUser userDB = null;
		if(user.getId() != null) {
			userDB = adminUserService.findById(user.getId()); 
			userDB.setName(user.getName());
			userDB.setEmail(user.getEmail());
			userDB.setActive(user.isActive());
		} else {
			userDB = user;
		}		
		return adminUserService.save(userDB);
	}
	
	@PostMapping(path = {"delete"})
	@ResponseBody
	public void delete(Long id) {	
		//adminUserService.deleteById(id);
	}
	
	@PostMapping(path = {"findByEmail"})
	@ResponseBody
	public AdminUser findByEmail(String email) {	
		return adminUserService.findByEmail(email);
	}
	
	@PostMapping(path = {"initPassword"})
	@ResponseBody
	public boolean initPassword(Long id) {	
		return adminUserService.initPassword(id);
	}
	
	@PostMapping(path = {"changePassword"})
	@ResponseBody
	public boolean changePassword(String password, Authentication authentication) {	
		return adminUserService.updatePassword(password, ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername()) > 0;
	}
	
}
