/**
 * 
 */
package com.lmching.mall.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
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
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/doLogin")
		.usernameParameter("email")
		.passwordParameter("password")
		.successHandler(new SimpleUrlAuthenticationSuccessHandler("/user"))
		.failureHandler(new SimpleUrlAuthenticationFailureHandler("/login"))
		.and()
		.authorizeRequests()
				.antMatchers("/login", 
						"/doLogin")
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
			.logoutUrl("/logout")
//			.logoutSuccessHandler(new HkdaSimpleUrlLogoutSuccessHandler())
			.logoutSuccessUrl("/index")
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

