package com.neonq.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class OrderResponseDTO {

    private Map<Long, OrderStatusDTO> orderStatus;
}
