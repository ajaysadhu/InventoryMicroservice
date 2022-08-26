package com.neonq.inventory.service;

import com.neonq.inventory.dto.OrderStatusDTO;
import com.neonq.inventory.dto.ProductDTO;
import com.neonq.inventory.dto.ProductOrderDTO;
import com.neonq.inventory.dto.ProductOrderListDTO;
import com.neonq.inventory.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class ProductOrderHelper {

    @Autowired
    ProductService productService;


    public HashMap<Long, OrderStatusDTO> orderProducts(ProductOrderListDTO productOrders) {
        HashMap<Long, OrderStatusDTO> allOrdersResponse = new HashMap<>();
        for(ProductOrderDTO order : productOrders.getOrders()) {
            OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
            try {
                orderStatusDTO.setStatus(productService.orderProductById(order.getProductId(), order.getQuantity()));
                orderStatusDTO.setMessage("Successfully Order Placed");
            } catch (ObjectOptimisticLockingFailureException lockingFailureException) {
                orderStatusDTO.setStatus("Failed");
                orderStatusDTO.setMessage("Failed due to High Concurrent Updates");
                log.error("Optimistic Lock Exception {}", lockingFailureException.getMessage());
            }catch (ResourceNotFoundException resourceEx) {
                orderStatusDTO.setStatus("Failed");
                orderStatusDTO.setMessage(resourceEx.getMessage());
                log.error("ResourceNotFoundException Exception {}", resourceEx.getMessage());
            }
            catch (Exception ex) {
                orderStatusDTO.setStatus("Failed");
                orderStatusDTO.setMessage("Unsuccessful in Order Placement. Try Again later");
                log.error("Exception {}", ex.getMessage());
            }
            allOrdersResponse.put(order.getProductId(), orderStatusDTO);
        }
        return allOrdersResponse;
    }
}
