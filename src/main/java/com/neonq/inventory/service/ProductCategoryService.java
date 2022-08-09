package com.neonq.inventory.service;

import com.neonq.inventory.dto.ProductCategoryDTO;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryDTO> getAllCategories();

    ProductCategoryDTO createProductCategory(ProductCategoryDTO dto);

    void deleteProductCategory(Long id);
}
