/**
 * 
 */
package com.lmching.mall.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lmching.mall.model.User;
import com.lmching.mall.service.UserService;

@Configuration
public class MallSecurityConfig extends WebSecurityConfigurerAdapter {	
	
	@Autowired
	UserService userService;
	
	@Autowired
	SessionLocaleResolver localeResolver;
	
	@Autowired
	ResourceBundleMessageSource messageSource;
	
    @Bean
	public UserDetailsService userDetailsService() {
    	return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {				
				User user = userService.findByEmail(email);
				return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isActive(), true, true, !user.isLock(), AuthorityUtils.commaSeparatedStringToAuthorityList(user.getType().name()));									
			}
		};
    }
	
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
    	return new HkdaAuthenticationSuccessHandler("/submit", userService);
    }
    
    class HkdaAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {    	
    	UserService userService;
    	
    	public HkdaAuthenticationSuccessHandler(String defaultSuccessUrl, UserService userService) {
    		this.setDefaultTargetUrl(defaultSuccessUrl);
    		this.setAlwaysUseDefaultTargetUrl(true);
    		this.userService = userService;
    	}    	
		
		@Override
		public void onAuthenticationSuccess(HttpServletRequest request,
				HttpServletResponse response, Authentication authentication)
				throws ServletException, IOException {
			RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
			request.getSession().setAttribute("loginFailMsg", "");
			String email = request.getParameter("login_email");
			userService.successLogin(email);
			User user = userService.findByEmail(email);
			if(request.getHeader("Referer") != null && request.getHeader("Referer").contains("/admin") && !"admin".equals(email)) {
				SecurityContextHolder.clearContext();
				request.getSession().setAttribute("adminLoginFailMsg", "Invalidate Username/Password");
    			redirectStrategy.sendRedirect(request, response, "/adminLogin");
    		} else if ("admin".equals(email)) {
				redirectStrategy.sendRedirect(request, response, "/admin");
				request.getSession().setAttribute("adminLoginFailMsg", "");
			} else {
				super.onAuthenticationSuccess(request, response, authentication);				
			}
		}
		
	}
	
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
    	return new HkdaAuthenticationFailureHandler("/login", userService);
    }  
    
    class HkdaSimpleUrlLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    	
    	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
    			Authentication authentication) throws IOException, ServletException {
    		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    		if(request.getHeader("Referer") != null && request.getHeader("Referer").contains("/admin")) {
    			redirectStrategy.sendRedirect(request, response, "/adminLogin");				    			
    		} else redirectStrategy.sendRedirect(request, response, "/login");
    	}
    }
    
    class HkdaAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {    	
    	UserService userService;
    	
    	public HkdaAuthenticationFailureHandler(String defaultFailureUrl, UserService userService) {
    		super(defaultFailureUrl);
    		this.userService = userService;
    	}
    	
		@Override
		public void onAuthenticationFailure(HttpServletRequest request,
				HttpServletResponse response, AuthenticationException exception)
						throws IOException, ServletException {
			if(request.getHeader("Referer") != null && request.getHeader("Referer").contains("/adminLogin")) {
				request.getSession().setAttribute("adminLoginFailMsg", "Invalidate Username/Password");
				RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
				redirectStrategy.sendRedirect(request, response, "/adminLogin");				
			} else {
				String email = request.getParameter("login_email");
				userService.failLogin(email);
				Locale locale = localeResolver.resolveLocale(request);
				if(exception instanceof LockedException) {
					String errorMsg = messageSource.getMessage("login.loginbox.account_locked_error", null, locale);
					request.getSession().setAttribute("loginFailMsg", errorMsg);
				}if(exception instanceof DisabledException) {
					String errorMsg = messageSource.getMessage("login.loginbox.account_inactive", null, locale);
					request.getSession().setAttribute("loginFailMsg", errorMsg);
				} else {
					String errorMsg = messageSource.getMessage("login.loginbox.no_record_match_error", null, locale);
					request.getSession().setAttribute("loginFailMsg", errorMsg);
				}
				super.onAuthenticationFailure(request, response, exception);				
			}
		}
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/doLogin")
		.usernameParameter("login_email")
		.passwordParameter("login_password")
//		.failureForwardUrl("/login")
//		.defaultSuccessUrl("/introduction")
		.successHandler(authenticationSuccessHandler())
		.failureHandler(authenticationFailureHandler())
		.and()
		.authorizeRequests()
				.antMatchers("/login", 
						"/doLogin", 
						"/head", 
						"/header", 
						"/register",
						"/forgetpassword",
						"/forgetpassword/*",
						"/doforgetpassword",
						"/changeLanguage/*", 
						"/active/*", 
						"/resetPassword", 
						"/resetPassword/*",
						"/paySuccess",
						"/paySuccess/*",
						"/payFail",
						"/payFail/*",
						"/adminLogin",
						"/ipn",
						"/index",
						"/category",
						"/list",
						"/result","/","/signin/**","/signup/**")
					.permitAll()					
				.antMatchers("/admin", "confirmWithPayaplPaid/**", "/confirmPaid/**", "/resetPaypalStatus/**", "/resetPaid/**", "/report", "/uploadImages", "/uploadAwards", "/downloadAwards")
					.hasAnyAuthority("ADMIN")
				.anyRequest()
				.authenticated()
				.and()
				.sessionManagement().maximumSessions(1)
				.and().and()
				.csrf().disable();
		http.logout()
//			.logoutUrl("/logout")
			.logoutSuccessHandler(new HkdaSimpleUrlLogoutSuccessHandler())
			.logoutSuccessUrl("/login")
			.and().csrf().disable();
		
	}
	
	public void configure(WebSecurity web) throws Exception {
	    web
        	.ignoring()
        	.antMatchers("/css/**", "/images/**", "/script/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

