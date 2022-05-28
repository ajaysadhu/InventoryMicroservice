package com.neonq.inventory.controller;

import com.neonq.inventory.dto.ProductCategoryResponse;
import com.neonq.inventory.dto.ProductRequestDTO;
import com.neonq.inventory.dto.ResponseMessage;
import com.neonq.inventory.model.Product;
import com.neonq.inventory.model.ProductCategory;
import com.neonq.inventory.service.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService pService;

    /**
     * \
     * http://localhost:9090/products?search=word - Request Parameter
     * http://localhost:9090/products/123 Path Variable
     */
    // Read
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable long id) {
        if (pService.getProduct(id).isPresent()) {
            return pService.getProduct(id).get();
        } else {
            throw new RuntimeException("Product Id does not exist");
        }

    }

    // Get All Product Categories
    @GetMapping("/categories")
    public ProductCategoryResponse getAllProductCategories() {
        return pService.getAllCategories();

    }

    @GetMapping
    public Page<Product> getProduct(@RequestParam(required = false) String search, @RequestParam(required = false) String category, @RequestParam(required = true) Integer currentPage) {
        Page<Product> page = null;

        if (!StringUtils.hasText(search) && !StringUtils.hasText(category)) {
            page = pService.getProductsByPageNum(currentPage - 1);
        } else if (!StringUtils.hasText(search) && StringUtils.hasText(category)) {
            page = pService.getProductsByCat(category, currentPage - 1);
        } else if (StringUtils.hasText(search) && !StringUtils.hasText(category)) {
            page = pService.getProductsBySearchKeyword(search, currentPage - 1);
        }
        return page;
    }


    // Create
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Product newProduct(@RequestBody @NotNull ProductRequestDTO product) {
        ProductCategory prodCat = pService.createProdCatIfAbsent(product.getCategory().toUpperCase());
        return pService.createProduct(product, prodCat);

    }

    // Update
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Product updateProduct(@PathVariable long id, @RequestBody ProductRequestDTO product) throws RuntimeException {
        Optional<Product> productFromDB = pService.getProduct(id);
        if (productFromDB.isPresent() && productFromDB.get().getName().equalsIgnoreCase(product.getName())) {
            // Add Product Category
            return pService.updateProduct(productFromDB.get(), product);
        } else {
            throw new RuntimeException("Product Name cannot be updated");
        }
    }

    //Delete
    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage deleteProduct(@PathVariable long id) {
        Optional<Product> productFromDB = pService.getProduct(id);
        if (productFromDB.isPresent()) {
            boolean success = pService.deleteProduct(productFromDB.get());
            if (success) {
                return new ResponseMessage(id + " Successfully Deleted", false);
            } else {
                return new ResponseMessage(id + " Cannot Delete Product, Check Connection/Permissions", true);
            }

        } else {
            return new ResponseMessage("Product Information is wrong", true);
        }
    }

}
