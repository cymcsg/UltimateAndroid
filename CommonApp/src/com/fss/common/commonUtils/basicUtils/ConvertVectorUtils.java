package com.fss.common.commonUtils.basicUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cym on 14-8-13.
 */
public class ConvertVectorUtils {
    public static <T> List<T> convertArrayToList(T t) {
        return Arrays.asList(t);
    }

    public static Object[] convertListToArray(List list) {
        return list.toArray();
    }
}
