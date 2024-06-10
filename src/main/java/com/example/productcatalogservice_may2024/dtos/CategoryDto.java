package com.example.productcatalogservice_may2024.dtos;


import com.example.productcatalogservice_may2024.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryDto {

    private Long id;
    private String name;
    private String description;

    //private List<Product> productList = new ArrayList<>();
}
