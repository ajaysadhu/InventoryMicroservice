package com.neonq.inventory.dto.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class OrderResponseDTO {

    private Map<Long, OrderItemDTO> orders;
    private OrderStatus status;

    public OrderResponseDTO() {
        this.orders = new HashMap<>();
        this.status  = OrderStatus.FAILURE;
    }
}
