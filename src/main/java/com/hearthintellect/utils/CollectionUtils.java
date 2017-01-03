package com.hearthintellect.utils;
import java.util.List;
import java.util.function.Function;

public class CollectionUtils {

    public static <T, K extends Comparable<? super K>> T binarySearch(List<? extends T> cols,
                                                                      K key,
                                                                      Function<T, K> getter) {
        if (cols.isEmpty())
            return null;
        int i = 0;
        int j = cols.size() - 1;
        int idx;
        while (i <= j) {
            idx = (i + j) / 2;
            T elem = cols.get(idx);
            K elemK = getter.apply(elem);
            if (elemK.equals(key))
                return elem;
            else if (elemK.compareTo(key) < 0)
                i = idx + 1;
            else
                j = idx - 1;
        }
        return null;
    }
}
