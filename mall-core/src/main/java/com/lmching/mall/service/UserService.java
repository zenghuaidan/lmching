package com.lmching.mall.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lmching.mall.model.User;

@Transactional
public interface UserService {

    public Optional<User> findById(Long id);
    
    public User findByEmail(String email);
    
    public boolean activeUser(String activeCode);

    public void save(User user);
    
    public void addNewUser(User user, HttpServletRequest request, SessionLocaleResolver localeResolver, ResourceBundleMessageSource messageSource);

    public void edit(User user);

    public boolean forgetPassword(String password, String email);
    
    public int updatePassword(String password, String email);
    
    public void failLogin(String email);

    public void successLogin(String email);

	public boolean updatePasswordByResetPasswordCode(String password, String resetPasswordCode);

	public boolean forgetPassword(String email, HttpServletRequest request, SessionLocaleResolver localeResolver, ResourceBundleMessageSource messageSource);
	
	public Iterable<User> findAll();
    
}