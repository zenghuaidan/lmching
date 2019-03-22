package com.lmching.mall.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

import com.lmching.mall.model.User;
import com.lmching.mall.repository.UserRepository;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {
 
    @Autowired
    private UserRepository userRepository;
 
    @Override
    public String execute(Connection<?> connection) {
        User user = new User();
        user.setName(connection.getDisplayName());
        user.setPassword("");
        userRepository.save(user);
        return user.getName();
    }
}