package com.store.services;

import com.store.dtos.PageableResponse;
import com.store.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto create(ProductDto productDto);
    ProductDto update(ProductDto productDto, String productId);
    void delete(String productId);
    ProductDto getSingleProduct(String productId);
    PageableResponse<ProductDto> getAll(int pageNumber,int pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> searchByTitle(String subtitle,int pageNumber,int pageSize, String sortBy, String sortDir);

    ////Create product with category Id
    ProductDto createProductWithCategory(ProductDto productDto,String categoryId);

    //Update category of existing product

    ProductDto updateProductWithCategory(String productId, String categoryId);

    //Get all product by Category id

    PageableResponse<ProductDto> getAllProductByCategoryId(String categoryId, int pageNumber,int pageSize, String sortBy, String sortDir);
}
