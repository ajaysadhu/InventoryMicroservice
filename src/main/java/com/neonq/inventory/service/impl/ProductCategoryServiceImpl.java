package com.neonq.inventory.service.impl;

import com.neonq.inventory.dao.ProductCategoryDAO;
import com.neonq.inventory.dto.ProductCategoryDTO;
import com.neonq.inventory.exception.ResourceExistsWarning;
import com.neonq.inventory.model.ProductCategory;
import com.neonq.inventory.service.ProductCategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDAO productCategoryDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductCategoryDTO> getAllCategories() {
        return productCategoryDAO.findAll().stream().map(a -> modelMapper.map(a, ProductCategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ProductCategoryDTO createProductCategory(ProductCategoryDTO dto) throws ResourceExistsWarning {
        ProductCategory modelProductCategory = modelMapper.map(dto, ProductCategory.class);
        if(productCategoryDAO.findByCategoryName(modelProductCategory.getCategoryName()).size()>0) {
            throw new ResourceExistsWarning(modelProductCategory.getCategoryName()+" Already Exists");
        } else {
            ProductCategory newCategory = productCategoryDAO.save(modelProductCategory);
            return modelMapper.map(newCategory, ProductCategoryDTO.class);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteProductCategory(Long id){
        Assert.notNull(id, "Received id as null in deleteProductCategory");
        productCategoryDAO.deleteById(id);
    }
}
