package com.adil.nytimes.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adil on 2/13/16.
 */
public enum SortOrder {
    New("Newest"), Old("Oldest");

    private String value;
    private static final Map<String, SortOrder> lookup = new HashMap<>();

    SortOrder(String value){this.value = value;}

    static {
        for(SortOrder p: EnumSet.allOf(SortOrder.class))
            lookup.put(p.toString(), p);
    }

    public String getUrlParam(){
        return this.value.toLowerCase();
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static SortOrder get(String value){return lookup.get(value);}
}
