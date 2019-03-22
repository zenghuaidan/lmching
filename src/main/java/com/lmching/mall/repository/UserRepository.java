package com.lmching.mall.repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.lmching.mall.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findById(Long id);
    
    User findByEmail(String email);
    
    User findByActiveCode(String activeCode);
    
    void deleteById(Long id);
    
    @Modifying
    @Query("update User u set u.password = ?1 where u.email = ?2")
    int updatePassword(String password, String email);

    @Modifying
    @Query("update User u set u.password = ?1, u.numFail=0 where u.resetPasswordCode = ?2")
	int updatePasswordByResetPasswordCode(String password, String resetPasswordCode);
    
    @Query("select count(1) from User")
  	int userCount();                    
    
    @Query(value = "select u from User u",
    		countQuery = "select count(1) from User u")
    Page<User> findAll(Pageable pageable);
}