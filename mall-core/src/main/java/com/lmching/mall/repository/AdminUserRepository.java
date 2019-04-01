package com.lmching.mall.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.lmching.mall.model.AdminUser;

public interface AdminUserRepository extends PagingAndSortingRepository<AdminUser, Long> {

    AdminUser findById(Long id);
    
    AdminUser findByEmail(String email);       
    
    void deleteById(Long id);
    
    @Modifying
    @Query("update AdminUser u set u.password = ?1 where u.email = ?2")
    int updatePassword(String password, String email);

    @Modifying
    @Query("update AdminUser u set u.password = ?1 where u.resetPasswordCode = ?2")
	int updatePasswordByResetPasswordCode(String password, String resetPasswordCode);
    
    @Query("select count(1) from AdminUser")
  	int userCount();                    
    
    @Query(value = "select u from AdminUser u",
    		countQuery = "select count(1) from AdminUser u")
    Page<AdminUser> findAll(Pageable pageable);
}