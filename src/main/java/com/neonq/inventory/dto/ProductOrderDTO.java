package com.neonq.inventory.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;


@Data
public class ProductOrderDTO {

    @NotEmpty
    @Positive
    private Long productId;
    @Positive
    @NotEmpty
    private int quantity;
}
