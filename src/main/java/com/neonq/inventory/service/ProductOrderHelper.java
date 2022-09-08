package com.neonq.inventory.service;

import com.neonq.inventory.dto.*;
import com.neonq.inventory.exception.ResourceNotFoundException;
import com.neonq.inventory.exception.StockUnavailableException;
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


    public OrderResponseDTO orderProducts(ProductOrderListDTO productOrders) {
        HashMap<Long, OrderItemResponseDTO> allOrdersResponse = new HashMap<>();
        int failureCount = 0;

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        for(ProductOrderDTO order : productOrders.getOrders()) {
            OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
            try {
                orderItemResponseDTO = productService.orderProductById(order.getProductId(), order.getQuantity());
            } catch (ObjectOptimisticLockingFailureException lockingFailureException) {
                orderItemResponseDTO.setStatus(OrderItemsStatuses.FAILURE);
                orderItemResponseDTO.setMessage("Failed due to High Concurrent Updates");
                failureCount++;
                log.error("Optimistic Lock Exception {}", lockingFailureException.getMessage());
            } catch (ResourceNotFoundException resourceNotFoundException) {
                log.error("ResourceNotFoundException {}", resourceNotFoundException.getMessage());
                orderItemResponseDTO.setAvailableStock(null);
                orderItemResponseDTO.setStatus(OrderItemsStatuses.FAILURE);
                orderItemResponseDTO.setMessage(resourceNotFoundException.getMessage());
                failureCount++;
            } catch (Exception ex) {
                orderItemResponseDTO.setStatus(OrderItemsStatuses.FAILURE);
                orderItemResponseDTO.setMessage("Unsuccessful in Order Placement. Try Again later");
                log.error("Exception {}", ex.getMessage());
                failureCount++;
            }
            allOrdersResponse.put(order.getProductId(), orderItemResponseDTO);
        }
        orderResponseDTO.setOrders(allOrdersResponse);
        if(failureCount == productOrders.getOrders().size()) {
            orderResponseDTO.setStatus(CompleteOrdersStatuses.FAILURE);
        } else if (failureCount < productOrders.getOrders().size()) {
            orderResponseDTO.setStatus(CompleteOrdersStatuses.PARTIAL_SUCCESS);
        } else {
            orderResponseDTO.setStatus(CompleteOrdersStatuses.SUCCESS);
        }
        return orderResponseDTO;
    }
}
