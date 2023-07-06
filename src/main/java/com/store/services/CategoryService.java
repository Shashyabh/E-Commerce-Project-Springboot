package com.store.services;

import com.store.dtos.CategoryDto;
import com.store.dtos.PageableResponse;

public interface CategoryService {

    //Create

    CategoryDto create(CategoryDto categoryDto);

    //Update

    CategoryDto update(CategoryDto categoryDto, String categoryId);

    //Delete
    void delete(String categoryId);

    //GetAll
    PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy, String sortDir);

    //Single category

    CategoryDto getSingleCategory(String categoryId);
}
