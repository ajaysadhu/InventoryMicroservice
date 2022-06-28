package com.neonq.inventory.service;

import com.neonq.inventory.dto.ProductCategoryDTO;
import com.neonq.inventory.exception.ResourceExistsWarning;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryDTO> getAllCategories();

    ProductCategoryDTO createProductCategory(ProductCategoryDTO dto) throws ResourceExistsWarning;
}
