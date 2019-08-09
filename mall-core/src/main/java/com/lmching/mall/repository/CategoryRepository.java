package com.lmching.mall.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lmching.mall.model.Category;
import com.lmching.mall.model.assist.CategoryType;


public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Iterable<Category> findByType(CategoryType type);
	
	@Query(value = "select c from Category c where c.pid = 0 and type = ?1")
	Iterable<Category> findMainCategoryByType(CategoryType type);
	
    @Query(value = "select c from Category c where c.pid <> 0 and type = ?1")
	Iterable<Category> findSubCategoryByType(CategoryType type);

    @Modifying
    @Query("delete Category c where c.pid = ?1")
	int deleteByPid(Long pid);
}