package com.marshalchen.ua.common.commonUtils.basicUtils;

import java.util.Arrays;
import java.util.List;

/**
 * An easy way to convert array,list,etc.
 */
public class ConvertVectorUtils {
    public static <T> List<T> convertArrayToList(T t) {
        return Arrays.asList(t);
    }

    public static Object[] convertListToArray(List list) {
        return list.toArray();
    }
}
