package com.lmching.mall.facebook;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

@Configuration
@EnableSocial
public class FacebookConfiguration extends SocialConfigurerAdapter {
	@Autowired
	public FacebookProperties properties;
	
	@Autowired
	public ConnectionSignUp connectionSignUp;
	
	@Autowired
	DataSource dataSource;
	
//	@Override
//	public UserIdSource getUserIdSource() {
//		return new UserIdSource() {
//			
//			@Override
//			public String getUserId() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		};
//	}
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
		connectionFactoryConfigurer.addConnectionFactory(new FacebookConnectionFactory(this.properties.getAppId(), this.properties.getAppSecret(),
				this.properties.getApiVersion()));
	}
	
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
		repository.setConnectionSignUp(connectionSignUp);		
		return repository;
	}

	// 提供Facebook 实例
	@Bean
	@ConditionalOnMissingBean(Facebook.class)
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Facebook facebook(ConnectionRepository repository) {
		Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
		return connection != null ? connection.getApi() : null;
	}

}