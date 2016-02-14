package com.adil.nytimes.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adil on 2/13/16.
 */
public enum NewsDesk {
    ARTS("Arts"), FASHION("Fashion Style"), SPORTS("Sports");

    private String value;
    private static final Map<String, NewsDesk> lookup = new HashMap<>();

    static {
        for(NewsDesk p: EnumSet.allOf(NewsDesk.class))
            lookup.put(p.toString(), p);
    }

    NewsDesk(String value){ this.value = value; }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public static NewsDesk get(String value) { return lookup.get(value);}
}
