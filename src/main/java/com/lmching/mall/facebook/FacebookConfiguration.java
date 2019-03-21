package com.lmching.mall.facebook;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.api.Facebook;

@Configuration
@EnableSocial
@EnableConfigurationProperties(EnhancedFacebookProperties.class)
public class FacebookConfiguration extends SocialConfigurerAdapter {
	@Autowired
	private EnhancedFacebookProperties properties;
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
		connectionFactoryConfigurer.addConnectionFactory(new CustomFacebookConnectionFactory(this.properties.getAppId(), this.properties.getAppSecret(),
				this.properties.getApiVersion()));
	}
	
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
		repository.setTablePrefix("imooc_");
		if(connectionSignUp != null) {
			repository.setConnectionSignUp(connectionSignUp);
		}
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

	// 当我们使用spring social的时候，我们会使用ConnectController 类来处理重定向的问题。默认情况下，spring
	// social会根据request URL ，自动构造redirect
	// URL，因为这个应用程序可能藏在代理下，provider没办法识别url,因此，我们在这里手动输入
	@Bean
	public ConnectController connectController(ConnectionFactoryLocator factoryLocator,
			ConnectionRepository repository) {
		ConnectController controller = new ConnectController(factoryLocator, repository);
		controller.setApplicationUrl("https://localhost:8080");
		return controller;
	}
}