package com.sentifi.assignment.config;

import com.sentifi.assignment.repository.JsonFileRepository;
import com.sentifi.assignment.repository.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by Sujay on 4/11/17.
 */

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    public Repository repository() {
        return new JsonFileRepository();
    }
}
