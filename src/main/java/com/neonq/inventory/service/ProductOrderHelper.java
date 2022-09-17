package com.neonq.inventory.service;

import com.neonq.inventory.dto.orders.*;
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


    public OrderResponseDTO orderProducts(OrderRequestDTO productOrders) {
        HashMap<Long, OrderItemDTO> allOrdersResponse = new HashMap<>();
        int failureCount = 0;

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        for(OrderDTO order : productOrders.getOrders()) {
            OrderItemDTO orderItemResponseDTO = new OrderItemDTO();
            try {
                orderItemResponseDTO = productService.orderProductById(order.getProductId(), order.getQuantity());
            } catch (ObjectOptimisticLockingFailureException lockingFailureException) {
                orderItemResponseDTO.setStatus(OrderItemStatus.FAILURE);
                orderItemResponseDTO.setMessage("Failed due to High Concurrent Updates");
                failureCount++;
                log.error("Optimistic Lock Exception {}", lockingFailureException.getMessage());
            } catch (ResourceNotFoundException resourceNotFoundException) {
                log.error("ResourceNotFoundException {}", resourceNotFoundException.getMessage());
                orderItemResponseDTO.setAvailableStock(null);
                orderItemResponseDTO.setStatus(OrderItemStatus.FAILURE);
                orderItemResponseDTO.setMessage(resourceNotFoundException.getMessage());
                failureCount++;
            } catch (Exception ex) {
                orderItemResponseDTO.setStatus(OrderItemStatus.FAILURE);
                orderItemResponseDTO.setMessage("Unsuccessful in Order Placement. Try Again later");
                log.error("Exception {}", ex.getMessage());
                failureCount++;
            }
            allOrdersResponse.put(order.getProductId(), orderItemResponseDTO);
        }
        orderResponseDTO.setOrders(allOrdersResponse);
        if(failureCount == productOrders.getOrders().size()) {
            orderResponseDTO.setStatus(OrderStatus.FAILURE);
        } else if (failureCount < productOrders.getOrders().size()) {
            orderResponseDTO.setStatus(OrderStatus.PARTIAL_SUCCESS);
        } else {
            orderResponseDTO.setStatus(OrderStatus.SUCCESS);
        }
        return orderResponseDTO;
    }
}
