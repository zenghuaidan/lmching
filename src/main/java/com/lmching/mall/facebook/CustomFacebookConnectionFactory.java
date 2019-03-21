package com.lmching.mall.facebook;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookAdapter;

//https://segmentfault.com/a/1190000015281172
//https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow

public class CustomFacebookConnectionFactory extends OAuth2ConnectionFactory<Facebook> {
	public CustomFacebookConnectionFactory(String appId, String appSecret, String apiVersion) {
		super("facebook", new CustomFacebookServiceProvider(appId, appSecret, apiVersion), new FacebookAdapter());
	}
}