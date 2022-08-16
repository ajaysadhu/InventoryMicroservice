package com.neonq.inventory.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class CommonUtils {

    private final static int PAGE_DISPLAY_COUNT = 5;

    public static Pageable getPageableObject(Integer currentPage, Integer pageSize, String sortBy) {
        return (StringUtils.hasText(sortBy))
                ? PageRequest.of(currentPage, CommonUtils.getPageDisplayCount(pageSize), Sort.by(sortBy))
                : PageRequest.of(currentPage, CommonUtils.getPageDisplayCount(pageSize));

    }

    public static int getPageDisplayCount(Integer pageSize) {
        return (pageSize == null || pageSize < 1) ? PAGE_DISPLAY_COUNT : pageSize;
    }

}
