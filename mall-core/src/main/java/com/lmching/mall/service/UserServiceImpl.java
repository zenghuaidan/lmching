package com.lmching.mall.service;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lmching.mall.model.User;
import com.lmching.mall.property.MallProperties;
import com.lmching.mall.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;  
	
	@Autowired
	MallProperties mallProperties;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	EmailService emailService;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void edit(User user) {
        userRepository.save(user);
    }

	@Override
	public int updatePassword(String password, String email) {
		return userRepository.updatePassword(password, email);
	}
	
	@Override
	public boolean updatePasswordByResetPasswordCode(String newPassword, String resetPasswordCode) {
		if(User.isValidPassowd(newPassword)) {
			return userRepository.updatePasswordByResetPasswordCode(passwordEncoder.encode(newPassword), resetPasswordCode) >= 1;			
		}
		return false;
	}

	@Override
	public void failLogin(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			user.setNumFail(user.getNumFail() + 1);
			userRepository.save(user);
		}
	}

	@Override
	public void successLogin(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			user.setNumFail(0);
			userRepository.save(user);
		}
	}

	@Override
	public boolean forgetPassword(String password, String email) {
		User user = userRepository.findByEmail(email);
		if(user != null) {
			user.setPassword(password);
			user.setNumFail(0);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean activeUser(String activeCode) {
		User user = userRepository.findByActiveCode(activeCode);
		if(user != null) {
//			user.setActiveCode("");
			user.setActive(true);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public void addNewUser(User user, HttpServletRequest request, SessionLocaleResolver localeResolver, ResourceBundleMessageSource messageSource) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		String activeCode = UUID.randomUUID().toString();
		user.setActiveCode(activeCode);
		Locale locale = localeResolver.resolveLocale(request);		
		String subject = messageSource.getMessage("login.register.active_user.subject", null, locale);
		String content = messageSource.getMessage("login.register.active_user.content", new String[] {user.getName(),  mallProperties.getDomain() + request.getContextPath() + "/active/" + activeCode}, locale);
		emailService.sendSimpleMail(user.getEmail(), subject, content);				
		save(user);	
	}
	
	@Override
	public boolean forgetPassword(String email, HttpServletRequest request, SessionLocaleResolver localeResolver, ResourceBundleMessageSource messageSource) {
		User user = findByEmail(email);
		if(user != null) {
			user.setResetPasswordCode(UUID.randomUUID().toString());
			userRepository.save(user);
			Locale locale = localeResolver.resolveLocale(request);
			String subject = messageSource.getMessage("forgetpassword.subject", null, locale);
			String content = messageSource.getMessage("forgetpassword.content", new String[] {user.getName(), mallProperties.getDomain() + request.getContextPath() + "/resetPassword/" + user.getResetPasswordCode()}, locale);
			emailService.sendSimpleMail(email, subject, content);
			return true;
		}
		return false;
	}
	
	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll(new Sort(new Order(Direction.ASC, "id")));
	}
	
}