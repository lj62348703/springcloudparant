package com.zy.config;

import com.zy.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfig {

    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }
}