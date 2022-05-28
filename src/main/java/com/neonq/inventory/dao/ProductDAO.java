package com.neonq.inventory.dao;

import com.neonq.inventory.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProductDAO extends JpaRepository<Product, Long> {

    @Query(value="SELECT * FROM product WHERE name like %:name%",
            countQuery = "SELECT count(*) FROM product WHERE name like %:name%",
            nativeQuery = true)
    public Page<Product> findByName(@Param("name") String name, Pageable pageable);

    @Query(value = "SELECT * FROM product WHERE category_id = ?1 ORDER BY ?1" ,
            countQuery = "SELECT count(*) FROM product WHERE category_id = ?1",
            nativeQuery = true)
    public Page<Product> findByCategory(@Param("categoryId") Long categoryId, Pageable pageable);
}