package com.lmching.mall.service;

import javax.transaction.Transactional;

import com.lmching.mall.model.AdminUser;


@Transactional
public interface AdminUserService {

	public Iterable<AdminUser> findAll();
	
	public AdminUser findById(Long id);
    
    public AdminUser findByEmail(String email);    

    public AdminUser save(AdminUser user);
    
    public int updatePassword(String password, String email);

	public boolean updatePasswordByResetPasswordCode(String password, String resetPasswordCode);

	public boolean forgetPassword(String email);
	
	void deleteById(Long id);
    
}