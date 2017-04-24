package com.sentifi.assignment.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sbhowmick on 4/24/17.
 */

@Configuration
public class HazelcastCacheConfig {

    private static final int TIME_TO_LIVE_IN_SECONDS = 24 * 60 * 60;
    private static final String TICKER_CACHE_NAME = "allTickerCache";
    private static final int MAX_TICKER_ENTRIES = 1000;

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        config.setInstanceName("hazelcast-config");

        MapConfig allTickerCache = new MapConfig();
        allTickerCache.setTimeToLiveSeconds(TIME_TO_LIVE_IN_SECONDS); // 1 Day
        allTickerCache.setEvictionPolicy(EvictionPolicy.LRU);
        MaxSizeConfig sizeConfig = new MaxSizeConfig(MAX_TICKER_ENTRIES, MaxSizeConfig.MaxSizePolicy.PER_NODE);
        allTickerCache.setMaxSizeConfig(sizeConfig);
        config.getMapConfigs().put(TICKER_CACHE_NAME, allTickerCache);

        return config;

    }
}
