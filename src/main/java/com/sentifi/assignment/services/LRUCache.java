package com.sentifi.assignment.services;

import com.sentifi.assignment.domain.Ticker;

/**
 * Created by Sujay on 4/14/17.
 */
public class LRUCache {
    private final LinkedHashMapWithCapacity<String, Ticker> linkedHashMap;

    public LRUCache(int capacity){
        this.linkedHashMap = new LinkedHashMapWithCapacity<>(capacity);
    }

    public Ticker get(String key){
        return this.linkedHashMap.get(key);
    }

    public void set(String key, Ticker ticker){
        this.linkedHashMap.put(key, ticker);
    }


}
