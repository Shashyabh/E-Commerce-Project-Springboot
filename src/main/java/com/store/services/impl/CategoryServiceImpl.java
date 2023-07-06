package com.store.services.impl;

import com.store.dtos.CategoryDto;
import com.store.dtos.PageableResponse;
import com.store.entities.Category;
import com.store.helper.Helper;
import com.store.repositories.CategoryRepo;
import com.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        //Creating category id randomly

        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category saveCategory = categoryRepo.save(category);
        CategoryDto categoryDto1 = modelMapper.map(saveCategory, CategoryDto.class);
        return categoryDto1;
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResolutionException("Category not found"));
        //Update category details

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category category1 = categoryRepo.save(category);
        CategoryDto categoryDto1 = modelMapper.map(category1, CategoryDto.class);
        return categoryDto1;
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResolutionException("Category not found"));
        categoryRepo.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = categoryRepo.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;
    }

    @Override
    public CategoryDto getSingleCategory(String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResolutionException("Category not found"));
        return modelMapper.map(category,CategoryDto.class);
    }
}
