package com.example.productcatalogservice_may2024.controllers;

import com.example.productcatalogservice_may2024.dtos.CategoryDto;
import com.example.productcatalogservice_may2024.dtos.ProductDto;
import com.example.productcatalogservice_may2024.models.Category;
import com.example.productcatalogservice_may2024.models.Product;
import com.example.productcatalogservice_may2024.services.FakeStorageProductService;
import com.example.productcatalogservice_may2024.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products") //This tag we are adding bcoz we are again and again adding "/products" in every method, to remove that we are adding this
//@Controller ->>If we use this tag, then we need to add again and again @responseBody. And we use @responseBody
// because if we don't use this then response can be any.
public class ProductController {

    @Autowired
    IProductService iProductService;

    @GetMapping
    public List<ProductDto> getAllProducts(){
        List<ProductDto> results = new ArrayList<>();
        List<Product> products = iProductService.getAllProducts();
        for(Product product : products){
            results.add(getProductDto(product));
        }
        return results;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId){ //Previously it returns product, now i have change to producDto because client interacts with Dto's
//        Product product = new Product();
//        product.setId(id);
//        product.setName("IPhone");
//        product.setDescription("Iphone 15");
//        return product;
        try {
            if(productId<=0){
                throw new IllegalArgumentException("Invalid Id");
            }
            Product product = iProductService.getProductById(productId);
            ProductDto body = getProductDto(product);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Called by","Shalabh");
            return new ResponseEntity<>(body, headers,HttpStatus.OK);
        }catch (IllegalArgumentException ex){
            throw ex;
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ProductDto replaceProduct(@PathVariable Long id, @RequestBody ProductDto productDto){
        Product product = iProductService.replaceProduct(id,getProduct(productDto));
        return getProductDto(product);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto){ //same as above
        Product product = getProduct(productDto);
        Product result = iProductService.createProduct(product);
        return getProductDto(result);
    }

    private ProductDto getProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setPrice(product.getPrice());
//        CategoryDto categoryDto = new CategoryDto();
//        categoryDto.setName(product.getCategory().getName());
//        productDto.setCategory(categoryDto);
        if(product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());
            categoryDto.setId(product.getCategory().getId());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }

    private Product getProduct(ProductDto productDto){
        Product product = new Product();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setPrice(product.getPrice());
//        CategoryDto categoryDto = new CategoryDto();
//        categoryDto.setName(product.getCategory().getName());
//        productDto.setCategory(categoryDto);
        if(productDto.getCategory() != null) {
            Category category = new Category();
            category.setName(productDto.getCategory().getName());
            category.setId(productDto.getCategory().getId());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }
        return product;
    }


}
