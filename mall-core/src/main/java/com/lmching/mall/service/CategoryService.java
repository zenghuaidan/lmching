package com.lmching.mall.service;

import javax.transaction.Transactional;

import com.lmching.mall.model.Category;
import com.lmching.mall.model.assist.CategoryType;


@Transactional
public interface CategoryService {

	public Iterable<Category> findMainCategoryByType(CategoryType type);
	
	public Iterable<Category> findSubCategoryByType(CategoryType type);

    public Category save(Category category);
    
    public void deleteById(Long id);
    
    public Category findById(Long id);

}