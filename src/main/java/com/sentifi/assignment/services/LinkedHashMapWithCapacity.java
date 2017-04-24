package com.sentifi.assignment.services;

import com.sentifi.assignment.domain.Entity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Sujay on 4/14/17.
 */
public class LinkedHashMapWithCapacity<K, V extends Entity> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LinkedHashMapWithCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return this.size() > this.capacity;
    }
}
