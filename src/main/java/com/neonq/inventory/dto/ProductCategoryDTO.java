package com.neonq.inventory.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProductCategoryDTO {
    private Long id;
    private String categoryName;
}
