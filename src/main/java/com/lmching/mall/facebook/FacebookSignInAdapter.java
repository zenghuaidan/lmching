package com.lmching.mall.facebook;

import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

@Component
public class FacebookSignInAdapter implements SignInAdapter {
    @Override
    public String signIn(
      String localUserId, 
      Connection<?> connection, 
      NativeWebRequest request) {
         
        SecurityContextHolder.getContext().setAuthentication(
          new UsernamePasswordAuthenticationToken(
        		  localUserId, null, 
          Arrays.asList(new SimpleGrantedAuthority("FACEBOOK_USER"))));
         
        return null;
    }
}