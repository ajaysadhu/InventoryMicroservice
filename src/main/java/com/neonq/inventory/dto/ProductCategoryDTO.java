package com.neonq.inventory.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ToString
public class ProductCategoryDTO {
    private Long id;
    @NotEmpty
    private String categoryName;
}
