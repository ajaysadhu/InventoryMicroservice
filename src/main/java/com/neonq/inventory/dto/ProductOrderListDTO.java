package com.neonq.inventory.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductOrderListDTO {
    List<ProductOrderDTO> orders;
}
