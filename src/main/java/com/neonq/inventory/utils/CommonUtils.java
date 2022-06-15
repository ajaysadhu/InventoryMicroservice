package com.neonq.inventory.utils;

public class CommonUtils {

    private final static int PAGE_DISPLAY_COUNT = 5;

    public static int getPageDisplayCount(Integer pageSize) {
        return (pageSize == null || pageSize < 1) ? PAGE_DISPLAY_COUNT : pageSize;
    }

}
