package com.store.services.impl;

import com.store.dtos.PageableResponse;
import com.store.dtos.ProductDto;
import com.store.entities.Category;
import com.store.entities.Product;
import com.store.repositories.CategoryRepo;
import com.store.repositories.ProductRepo;
import com.store.exceptions.ResourceNotFoundException;
import com.store.helper.Helper;
import com.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);

        //Generate product id and date
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setAddedDate(new Date());

        Product savedProduct = this.productRepo.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setLive(productDto.isLive());
        product.setQuantity(productDto.getQuantity());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());
        Product save = productRepo.save(product);
        return modelMapper.map(save,ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepo.delete(product);
    }

    @Override
    public ProductDto getSingleProduct(String productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize, String sortBy, String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> page = productRepo.findAll(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepo.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subtitle,int pageNumber,int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepo.findByTitleContaining(subtitle,pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Product product = modelMapper.map(productDto, Product.class);

        //Generate product id and date
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setAddedDate(new Date());
        //Saving category Id
        product.setCategory(category);
        Product savedProduct = this.productRepo.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto updateProductWithCategory(String productId, String categoryId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        product.setCategory(category);
        Product savedProduct = productRepo.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProductByCategoryId(
            String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Sort sort=sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page=productRepo.findByCategory(category,pageable);

        return Helper.getPageableResponse(page,ProductDto.class);
    }
}
