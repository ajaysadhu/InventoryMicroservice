package com.neonq.inventory.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductCategoryResponse {

    List<String> categories;
}
