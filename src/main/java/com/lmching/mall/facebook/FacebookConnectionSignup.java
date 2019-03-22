package com.lmching.mall.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;

import com.lmching.mall.model.User;
import com.lmching.mall.model.UserType;
import com.lmching.mall.repository.UserRepository;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {
 
    @Autowired
    private UserRepository userRepository;
 
    @Override
    public String execute(Connection<?> connection) {
//    	org.springframework.social.facebook.api.User userProfile = ((Facebook)connection.getApi()).userOperations().getUserProfile();
    	
    	String [] fields = { "id", "about", "age_range", "birthday", "context", "cover", "currency", "devices", "education", "email", "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown", "inspirational_people", "installed", "install_type", "is_verified", "languages", "last_name", "link", "locale", "location", "meeting_for", "middle_name", "name", "name_format", "political", "quotes", "payment_pricepoints", "relationship_status", "religion", "security_settings", "significant_other", "sports", "test_group", "timezone", "third_party_id", "updated_time", "verified", "video_upload_limits", "viewer_can_send_gift", "website", "work"};//{ "id", "email",  "first_name", "last_name" };
//    	{ "id", "about", "age_range", "birthday", "context", "cover", "currency", "devices", "education", "email", "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown", "inspirational_people", "installed", "install_type", "is_verified", "languages", "last_name", "link", "locale", "location", "meeting_for", "middle_name", "name", "name_format", "political", "quotes", "payment_pricepoints", "relationship_status", "religion", "security_settings", "significant_other", "sports", "test_group", "timezone", "third_party_id", "updated_time", "verified", "video_upload_limits", "viewer_can_send_gift", "website", "work"}
    	org.springframework.social.facebook.api.User userProfile = (org.springframework.social.facebook.api.User)((Facebook)connection.getApi()).fetchObject("me", org.springframework.social.facebook.api.User.class, fields);
        User user = new User();
        user.setName(connection.getDisplayName());
        user.setType(UserType.Buyer);
        user.setEmail(userProfile.getEmail());        
        userRepository.save(user);
        return user.getId() + "";
    }
}