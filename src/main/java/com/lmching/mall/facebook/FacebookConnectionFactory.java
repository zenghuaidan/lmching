package com.lmching.mall.facebook;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookAdapter;

//https://segmentfault.com/a/1190000015281172
//https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow
//https://www.baeldung.com/facebook-authentication-with-spring-security-and-social

public class FacebookConnectionFactory extends OAuth2ConnectionFactory<Facebook> {
	public FacebookConnectionFactory(String appId, String appSecret, String apiVersion) {
		super("facebook", new FacebookServiceProvider(appId, appSecret, apiVersion), new FacebookAdapter());
	}
}