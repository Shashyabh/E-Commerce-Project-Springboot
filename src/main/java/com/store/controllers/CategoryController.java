package com.store.controllers;

import com.store.dtos.ApiResponse;
import com.store.dtos.CategoryDto;
import com.store.dtos.PageableResponse;
import com.store.dtos.ProductDto;
import com.store.services.CategoryService;
import com.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    //Create
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = this.categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //localhost:5000/categories/{categoryId}/products
    //categoryId will be dynamic in nature
    //Create product with category Id

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCAtegory(@RequestBody ProductDto productDto,@PathVariable String categoryId){

        ProductDto productDto1 = this.productService.createProductWithCategory(productDto, categoryId);
        return new ResponseEntity<>(productDto1,HttpStatus.CREATED);

    }

    //Already created product will be updated to specific category
    //localhost:5000/categories/{categoryId}/product/create/{productId}

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(@PathVariable String categoryId,@PathVariable String productId){
        ProductDto productDto = productService.updateProductWithCategory(productId, categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    //Get products by CategoryId
    @GetMapping("/{categoryId}")
    private ResponseEntity<PageableResponse<ProductDto>> getALlProductsByCategory(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        PageableResponse<ProductDto> response = productService.getAllProductByCategoryId(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //Update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable String categoryId){
        CategoryDto categoryDto1 = this.categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }

    //Delete
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deletCategory(@PathVariable String categoryId){
        this.categoryService.delete(categoryId);
        ApiResponse apiResponse = ApiResponse.builder().message("Category deleted successfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    //GetAll Categories
    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    //get single
    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){
        CategoryDto categoryDto = categoryService.getSingleCategory(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
}
