package com.zy.config;

import com.zy.filter.LogFilter;
import com.zy.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {

    @Bean
    public LogFilter tokenFilter(){
        return new LogFilter();
    }
}