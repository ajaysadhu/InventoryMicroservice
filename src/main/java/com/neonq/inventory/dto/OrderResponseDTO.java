package com.neonq.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class OrderResponseDTO {

    private Map<Long, OrderItemResponseDTO> orders;
    private CompleteOrdersStatuses status;

    public OrderResponseDTO() {
        this.orders = new HashMap<>();
        this.status  = CompleteOrdersStatuses.FAILURE;
    }
}
