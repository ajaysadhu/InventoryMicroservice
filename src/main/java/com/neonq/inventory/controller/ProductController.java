package com.neonq.inventory.controller;

import com.neonq.inventory.dto.ResponseMessage;
import com.neonq.inventory.model.Product;
import com.neonq.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
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
        if(pService.getProduct(id).isPresent()) {
            return pService.getProduct(id).get();
        } else {
            throw new RuntimeException("Product Id does not exist");
        }

    }

    @GetMapping("/getAll")
    public Page<Product> getProduct(@RequestParam String search) {

        return pService.getProductsBySearch(search);

    }


    // Create
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Product newProduct(@RequestBody Product product) {

        return pService.createOrUpdateProduct(product);

    }

    // Update
    @PutMapping(value="/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Product updateProduct(@PathVariable long id, @RequestBody Product product) throws RuntimeException {
        Optional<Product> productFromDB = pService.getProduct(id);
        if( productFromDB.isPresent() && productFromDB.get().getName().equalsIgnoreCase(product.getName())) {
            return pService.createOrUpdateProduct(product);

        } else {
            throw new RuntimeException("Product Information is wrong");
        }
    }
    //Delete
    @DeleteMapping(value="/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage deleteProduct(@PathVariable long id)  {
        Optional<Product> productFromDB = pService.getProduct(id);
        if(productFromDB.isPresent()) {
            pService.deleteProduct(productFromDB.get());
            return new ResponseMessage(id+" Successfully Deleted", false);
        } else {
            return new ResponseMessage("Product Information is wrong", true);
        }
    }

}
