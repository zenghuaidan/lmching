package com.lmching.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lmching.mall.model.Category;
import com.lmching.mall.model.assist.CategoryType;
import com.lmching.mall.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping(path = {"listBrands"})
	@ResponseBody
	public Iterable<Category> listBrands() {
		return categoryService.findMainCategoryByType(CategoryType.Brand);
	}
	
	@GetMapping(path = {"listTypes"})
	@ResponseBody
	public Iterable<Category> listTypes() {
		return categoryService.findMainCategoryByType(CategoryType.Type);
	}
	
	@GetMapping(path = {"listSubTypes"})
	@ResponseBody
	public Iterable<Category> listSubTypes() {
		return categoryService.findSubCategoryByType(CategoryType.Type);
	}
	
	@GetMapping(path = {"listSubBrands"})
	@ResponseBody
	public Iterable<Category> listSubBrands() {
		return categoryService.findSubCategoryByType(CategoryType.Brand);
	}
	
	@PostMapping(path = {"create", "update"})
	@ResponseBody
	public Category createOrUpdate(Category category) {
		Category categoryDB = null;
		if(category.getId() != null) {
			categoryDB = categoryService.findById(category.getId()); 
			categoryDB.setNameEN(category.getNameEN());
			categoryDB.setNameTC(category.getNameTC());
		} else {
			categoryDB = category;
		}		
		return categoryService.save(categoryDB);
	}
	
	@PostMapping(path = {"delete"})
	@ResponseBody
	public void delete(Long id) {	
		categoryService.deleteById(id);
	}
	
	@GetMapping(path = {"managesubcategory"})
	public String manageSubCategory(@RequestParam(value="type", required=true) String type, Model model) {
		CategoryType categoryType = CategoryType.valueOf(type);
		model.addAttribute("mainCategories", categoryService.findMainCategoryByType(categoryType));
		return "managesubcategory";
	}
	
}
