package com.lmching.mall.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lmching.mall.model.AdminUser;
import com.lmching.mall.model.User;
import com.lmching.mall.property.MallProperties;
import com.lmching.mall.repository.AdminUserRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;  
	
	@Autowired
	MallProperties mallProperties;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	EmailService emailService;

    @Override
    public AdminUser findById(Long id) {
        return adminUserRepository.findById(id);
    }
    
    @Override
    public AdminUser findByEmail(String email) {
        return adminUserRepository.findByEmail(email);
    }

    @Override
    public void save(AdminUser user) {
    	adminUserRepository.save(user);
    }

	@Override
	public int updatePassword(String password, String email) {
		return adminUserRepository.updatePassword(password, email);
	}
	
	@Override
	public boolean updatePasswordByResetPasswordCode(String newPassword, String resetPasswordCode) {
		if(User.isValidPassowd(newPassword)) {
			return adminUserRepository.updatePasswordByResetPasswordCode(passwordEncoder.encode(newPassword), resetPasswordCode) >= 1;			
		}
		return false;
	}
	
	@Override
	public boolean forgetPassword(String email) {
		AdminUser user = findByEmail(email);
		if(user != null) {
			user.setResetPasswordCode(UUID.randomUUID().toString());
			adminUserRepository.save(user);		
			String link = "";
			String subject = "Reset Password";
			String content = "Hi, " + user.getName() + ",<br/><br/>Please click on the <a target='_blank' href=\"" + link + "\">link</a> to reset your password for login to the system. <br/><br/>Sincerely,<br/>LMCHING Group";
			emailService.sendSimpleMail(email, subject, content);
			return true;
		}
		return false;
	}
	
}