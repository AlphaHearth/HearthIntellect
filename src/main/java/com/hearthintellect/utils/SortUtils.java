package com.hearthintellect.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public abstract class SortUtils {

    public static Sort parseSort(String sortQuery) {
        if (StringUtils.isBlank(sortQuery))
            return null;
        String[] sortFields = sortQuery.split(",");
        List<Sort.Order> orderList = new ArrayList<>(sortFields.length);
        for (String field : sortFields) {
            if (field.startsWith("-"))
                orderList.add(new Sort.Order(Sort.Direction.DESC, field.substring(1)));
            else
                orderList.add(new Sort.Order(Sort.Direction.ASC, field));
        }
        return new Sort(orderList);
    }
}
