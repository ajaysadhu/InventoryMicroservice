package com.neonq.inventory.service.impl;

import com.neonq.inventory.dao.ProductCategoryDAO;
import com.neonq.inventory.dao.ProductDAO;
import com.neonq.inventory.dto.orders.OrderItemDTO;
import com.neonq.inventory.dto.PageableProductDTO;
import com.neonq.inventory.dto.ProductDTO;
import com.neonq.inventory.dto.orders.OrderItemStatus;
import com.neonq.inventory.exception.ResourceNotFoundException;
import com.neonq.inventory.exception.StockUnavailableException;
import com.neonq.inventory.model.Product;
import com.neonq.inventory.model.ProductCategory;
import com.neonq.inventory.service.ProductService;
import com.neonq.inventory.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    ProductCategoryDAO productCategoryDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Product getProductById(Long id) throws ResourceNotFoundException {
        Assert.notNull(id, "Received Product Id as null in getProductById");
        return productDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Id does not exist"));
    }

    public void deleteProduct(Long id) {
        Assert.notNull(id, "Received Product Id as null in deleteProduct");
        productDAO.deleteById(id);
    }

    @Override
    public PageableProductDTO getProductsPerPage(Integer pageNum, Integer pageSize, String sortBy) {

        Pageable pageable = CommonUtils.getPageableObject(pageNum, pageSize, sortBy);

        PageableProductDTO result = new PageableProductDTO();
        Page<Product> productPage = productDAO.findAll(pageable);
        Type listType = new TypeToken<List<ProductDTO>>() {
        }.getType();
        result.setProductDTOList(modelMapper.map(productPage.toList(), listType));
        result.setTotalRecords(productPage.getTotalElements());
        result.setTotalPages(productPage.getTotalPages());
        result.setCurrentPage(pageNum + 1);
        return result;
    }

    public PageableProductDTO getProductsBySearchKeyword(String search, Integer pageNum, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNum, CommonUtils.getPageDisplayCount(pageSize));
        Page<Product> productPage = productDAO.findByKeyword(search, paging);
        if (productPage == null || productPage.getTotalElements() == 0) {
            return new PageableProductDTO();
        }
        PageableProductDTO result = new PageableProductDTO();
        Type listType = new TypeToken<List<ProductDTO>>() {
        }.getType();
        result.setProductDTOList(modelMapper.map(productPage.toList(), listType));
        result.setTotalRecords(productPage.getTotalElements());
        result.setTotalPages(productPage.getTotalPages());
        result.setCurrentPage(pageNum + 1);
        return result;
    }

    public ProductDTO createProduct(ProductDTO productDTO) throws ResourceNotFoundException {
        // TODO Error handling for id
        ProductCategory category = productCategoryDAO.findById(productDTO.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category ID doesn't exist"));

        modelMapper.typeMap(ProductDTO.class, Product.class).addMappings(mapper -> {
            // not setting category instead use the optional
            mapper.skip(Product::setCategory);
        });
        Product newProduct = modelMapper.map(productDTO, Product.class);

        newProduct.setCategory(category);

        return modelMapper.map(productDAO.save(newProduct), ProductDTO.class);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) throws ResourceNotFoundException {
        Assert.notNull(id, "Received Product Id as null in updateProduct");
        Product productFound = productDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Id passed doesn't exist"));
        ProductCategory categoryFound = productCategoryDAO.findById(productDTO.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product Category Id passed doesn't exist"));
        Product productToUpdate = productFound.toBuilder().active(productDTO.isActive())
                .description(productDTO.getDescription())
                .category(categoryFound)
                .unitsInStock(productDTO.getUnitsInStock())
                .unitPrice(productDTO.getUnitPrice()).build();

        return modelMapper.map(productDAO.save(productToUpdate), ProductDTO.class);
    }

    @Transactional
    public ProductDTO orderProduct(String sku, int quantity) throws StockUnavailableException {

        Product product = productDAO.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Product Sku doesn't exist"));

        Product productModel ;
        int unitsInStock = product.getUnitsInStock();
        if (unitsInStock >= quantity) {
            System.out.println("new quantity::" + (unitsInStock - quantity));

            product.setUnitsInStock(unitsInStock - quantity);
            productModel = productDAO.save(product);
        }  else {
            throw new StockUnavailableException("Product quantity problem");
        }
        return modelMapper.map(productModel, ProductDTO.class);

    }

    @Transactional
    @Override
    public OrderItemDTO orderProductById(Long productId, int quantity) throws ResourceNotFoundException{
        OrderItemDTO orderItemResponseDTO= new OrderItemDTO();
        Product product = productDAO.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Id does not exist"));

        int unitsInStock = product.getUnitsInStock();
        if (unitsInStock >= quantity) {
            log.info("new quantity::" + (unitsInStock - quantity));

            product.setUnitsInStock(unitsInStock - quantity);
            orderItemResponseDTO.setStatus(OrderItemStatus.SUCCESS);
            orderItemResponseDTO.setMessage("Order Placed Successfully");
            orderItemResponseDTO.setAvailableStock(unitsInStock - quantity);

        } else {
            orderItemResponseDTO.setStatus(OrderItemStatus.FAILURE);
            orderItemResponseDTO.setMessage("Stock Availability Limit");
            orderItemResponseDTO.setAvailableStock(unitsInStock);
        }
        return orderItemResponseDTO;
    }

}
