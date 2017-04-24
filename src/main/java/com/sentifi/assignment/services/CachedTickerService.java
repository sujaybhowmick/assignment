package com.sentifi.assignment.services;

/**
 * Created by Sujay on 4/14/17.
 */
public abstract class CachedTickerService implements TickerService {

    protected final LRUCache lruCache;

    public CachedTickerService(int capacity) {
        this.lruCache = new LRUCache(capacity);
    }
}
