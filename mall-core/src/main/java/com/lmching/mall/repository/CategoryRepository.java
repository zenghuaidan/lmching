package com.lmching.mall.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lmching.mall.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}