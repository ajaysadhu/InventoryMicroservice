package com.neonq.inventory.dto;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
   private String message;
   private OrderItemsStatuses status;
   private Integer availableStock;
}
