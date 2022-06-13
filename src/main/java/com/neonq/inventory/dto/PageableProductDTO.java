package com.neonq.inventory.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageableProductDTO {
    private int totalPages;
    private int currentPage;
    private long  totalRecords;
    private List<ProductDTO> productDTOList = new ArrayList<>();
}
