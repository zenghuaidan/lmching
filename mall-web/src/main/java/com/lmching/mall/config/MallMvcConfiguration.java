package com.lmching.mall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MallMvcConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	WebMvcProperties mvcProperties;		
	
	@Bean
	public SessionLocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(this.mvcProperties.getLocale());
		sessionLocaleResolver.setLocaleAttributeName("language");
		return sessionLocaleResolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/**");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}
	
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer){
		configurer.setUseSuffixPatternMatch(false);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//		yaml.setResources(new FileSystemResource("config.yml"));//File引入
		yaml.setResources(new ClassPathResource("country.yml"));//class引入
		configurer.setProperties(yaml.getObject());
		return configurer;
	}
	
//    private final long MAX_AGE_SECS = 3600;
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//        .allowedOrigins("*")
//        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//        .allowedHeaders("*")
//        .allowCredentials(true)
//        .maxAge(MAX_AGE_SECS);
//    }

}
