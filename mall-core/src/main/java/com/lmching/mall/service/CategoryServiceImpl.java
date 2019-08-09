package com.lmching.mall.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmching.mall.model.Category;
import com.lmching.mall.model.assist.CategoryType;
import com.lmching.mall.repository.CategoryRepository;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;  

    @Override
    public Category save(Category category) {
    	return categoryRepository.save(category);
    }

	@Override
	public Iterable<Category> findMainCategoryByType(CategoryType type) {
		return categoryRepository.findMainCategoryByType(type);
	}

	@Override
	public Iterable<Category> findSubCategoryByType(CategoryType type) {
		return categoryRepository.findSubCategoryByType(type);
	}

	@Override
	public void deleteById(Long id) {
		categoryRepository.deleteByPid(id);
		categoryRepository.delete(id);
	}

	@Override
	public Category findById(Long id) {
		return categoryRepository.findOne(id);
	}

}