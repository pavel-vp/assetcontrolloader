package com.ac.loader.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that wraps mapping
 */
public class MappingData {
    private Map<String, String> mapping = new HashMap<>();

    public String convert(String original) {
        return mapping.get(original);
    }

    public void putMapping(String key, String value) {
        mapping.put(key, value);
    }
}
