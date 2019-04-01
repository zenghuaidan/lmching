/**
 * 
 */
package com.lmching.mall.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;

import com.lmching.mall.model.AdminUser;
import com.lmching.mall.service.AdminUserService;

@Configuration
public class MallSecurityConfig extends WebSecurityConfigurerAdapter {	
	
	@Autowired
	AdminUserService adminUserService;
	
    @Bean
	public UserDetailsService userDetailsService() {
    	return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {				
				AdminUser user = adminUserService.findByEmail(email);
				return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(user.isAdmin() ? "Admin" : ""));									
			}
		};
    }
    
    @Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
    }
    
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/doLogin")
		.usernameParameter("email")
		.passwordParameter("password")
		.successHandler(new SimpleUrlAuthenticationSuccessHandler("/index"))
		.failureHandler(new SimpleUrlAuthenticationFailureHandler("/signin"))
		.and()
		.authorizeRequests()
				.antMatchers("/login", "/doLogin")
					.permitAll()
				.antMatchers("/adminuser/**")
					.hasAnyAuthority("Admin")
				.anyRequest()
				.authenticated()
				.and()
				.sessionManagement().maximumSessions(1)
				.and().and()
				.csrf().disable();
		http.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login")
			.and().csrf().disable();
		
	}
	
	public void configure(WebSecurity web) throws Exception {
	    web
        	.ignoring()
        	.antMatchers("/css/**", "/images/**", "/script/**", "/fonts/**", "/bootstrap/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

