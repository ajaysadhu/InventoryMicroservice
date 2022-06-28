package com.neonq.inventory.dao;

import com.neonq.inventory.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductCategoryDAO extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findByCategoryName(String category);
}
