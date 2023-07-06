package com.store.controllers;

import com.store.dtos.*;
import com.store.services.FileService;
import com.store.services.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
@Api(value = "Product Controller" ,description = "RestAPIs related to perform Product Operation")//Swagger
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;

    //localhost:5000/products/create
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductDto productDto1 = productService.create(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable String productId){
        ProductDto productDto1 = productService.update(productDto,productId);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId){
        productService.delete(productId);
        ApiResponse apiResponse = ApiResponse.builder().message("Product is deleted successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/getSingle/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId){
        ProductDto productDto1 = productService.getSingleProduct(productId);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    @GetMapping("/getAllLive")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductBySearcch(
            @PathVariable String query,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    //Upload image
    @PostMapping("/imageUpload/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage")MultipartFile image
            ) throws IOException {
        String fileName = fileService.uploadImage(image, imagePath);

        ProductDto productDto = productService.getSingleProduct(productId);
        productDto.setProductImageName(fileName);
        ProductDto updatedProduct = productService.update(productDto, productId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(updatedProduct.getProductImageName()).message("Product image is successfully updated").status(HttpStatus.CREATED).success(true).build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse httpServletResponse) throws IOException {
        ProductDto productDto = productService.getSingleProduct(productId);
        InputStream resource = fileService.getResource(imagePath, productDto.getProductImageName());
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,httpServletResponse.getOutputStream());
    }
}
