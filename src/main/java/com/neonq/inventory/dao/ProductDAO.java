package com.neonq.inventory.dao;

import com.neonq.inventory.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;


public interface ProductDAO extends JpaRepository<Product, Long> {

    @Query("FROM Product p WHERE p.name like %:keyword% or p.category.categoryName like %:keyword%")
    Page<Product> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Product  findBySku(String sku);
}
