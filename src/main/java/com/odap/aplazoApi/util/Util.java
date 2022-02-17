package com.odap.aplazoApi.util;

import java.util.Arrays;
import java.util.Objects;

public class Util {

    public enum STATUS{
        SUCCESS,
        ERROR,
        INVALID
    }


    public static boolean isBetween(Integer value,Integer min,Integer max){
        if(anyNulls(value,min,max)) return false;
        return value>= min && value<=max;
    }

    public static boolean isGreaterThanAndLessThan(Double value,Double min,Double max){
        if(anyNulls(value,min,max)) return false;
        return value > min && value < max;
    }


    public static boolean anyNulls(Object ... values){
        if(values == null) return true;
        return Arrays.stream(values).filter(Objects::isNull).count() > 0;
    }

}
