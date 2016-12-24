package com.hearthintellect.utils;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Sort {
    private static final Logger LOG = LoggerFactory.getLogger(Sort.class);

    private Map<String, SortOrder> sortings = new HashMap<>();

    /**
     * <p>
     *     Converts the given string representing a sorting order to the respective {@code Sort} instance
     * </p>
     * <p>
     *     Fields to be sorted by should be separated by commas, with preceding `-` meaning sort in descending order
     *     and without it meaning sort in ascending order.
     * </p>
     * <p>For examples:</p>
     * <ul>
     *     <li>"{@code price}" - sort by {@code price} in ascending order, same as {@code {price : 1}}</li>
     *     <li>"{@code -price}" - sort by {@code price} in descending order, same as {@code {price : -1}}</li>
     *     <li>
     *         "{@code price,-num_comments}" - sort by {@code price} in ascending order
     *         and {@code num_comments} in descending order, , same as {@code {price : 1, num_comments : -1}}
     *     </li>
     * </ul>
     * <p>
     *     <b>Note</b>: no space should be found in the given string, all spaces within the string will be seen
     *     as part of a field name.
     * </p>
     *
     * @param sort the given string representing a sorting order
     * @return the respective {@link Sort} instance
     */
    public static Sort of(String sort) {
        return new Sort(sort);
    }

    public Sort(String sort) {
        LOG.debug("Process sort string `" + sort + "`");

        String[] fields = sort.trim().split(",");

        for (String field : fields) {
            if (field.startsWith("-")) {
                LOG.trace("Sort field `" + field.substring(1) + "` in descending order");
                sortings.put(field.substring(1), SortOrder.DESCENDING);
            } else {
                LOG.trace("Sort field `" + field + "` in ascending order");
                sortings.put(field, SortOrder.ASCENDING);
            }
        }
    }

    /**
     * Sort the given field in given order
     *
     * @param field name of the given field
     * @param order the given order
     * @return this
     */
    public Sort put(String field, SortOrder order) {
        sortings.put(field, order);

        return this;
    }

    /**
     * Converts this instance into a {@link Document} that can be used
     * directly in {@link FindIterable#sort(Bson)}.
     *
     * @return the respective {@link Document}
     */
    public Document toDoc() {
        Document result = new Document();

        for (Map.Entry<String, SortOrder> entry : sortings.entrySet()) {
            if (entry.getValue() == SortOrder.DESCENDING)
                result.append(entry.getKey(), -1);
            else if (entry.getValue() == SortOrder.ASCENDING)
                result.append(entry.getKey(), 1);
        }

        return result;
    }

}
