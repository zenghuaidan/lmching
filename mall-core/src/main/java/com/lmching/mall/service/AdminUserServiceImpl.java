package com.lmching.mall.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lmching.mall.model.AdminUser;
import com.lmching.mall.model.User;
import com.lmching.mall.property.MallProperties;
import com.lmching.mall.repository.AdminUserRepository;


@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

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
    public AdminUser save(AdminUser user) {
    	return adminUserRepository.save(user);
    }

	@Override
	public int updatePassword(String password, String email) {
		if(StringUtils.isBlank(password)) return 0;
		return adminUserRepository.updatePassword(passwordEncoder.encode(password), email);
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
	
	@Override
	public boolean initPassword(Long id) {
		AdminUser user = findById(id);
		if(user != null) {
			String password = UUID.randomUUID().toString().substring(0, 6);
			user.setPassword(passwordEncoder.encode(password));
			adminUserRepository.save(user);		
			String subject = "New Password";
			String content = "Hi, " + user.getName() + ",<br/><br/>You password have been reset to:" + password + " <br/><br/>Sincerely,<br/>LMCHING Group";
			emailService.sendSimpleMail(user.getEmail(), subject, content);
			return true;
		}
		return false;
	}

	@Override
	public Iterable<AdminUser> findAll() {
		return adminUserRepository.findAll(new Sort(new Order(Direction.DESC, "id")));
	}

	@Override
	public void deleteById(Long id) {
		adminUserRepository.delete(id);
	}

}