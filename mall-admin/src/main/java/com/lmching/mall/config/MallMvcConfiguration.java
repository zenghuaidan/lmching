package com.lmching.mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MallMvcConfiguration extends WebMvcConfigurerAdapter {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/editor").setViewName("editor");
		registry.addViewController("/grid").setViewName("grid");
		registry.addViewController("/boot").setViewName("boot");
		registry.addViewController("/user").setViewName("user");
		registry.addViewController("/adminuser").setViewName("adminuser");
		registry.addViewController("/enquiry").setViewName("enquiry");
	}
	
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer){
		configurer.setUseSuffixPatternMatch(false);
	}

}
