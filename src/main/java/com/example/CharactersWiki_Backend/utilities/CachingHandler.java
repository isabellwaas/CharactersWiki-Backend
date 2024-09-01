package com.example.CharactersWiki_Backend.utilities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class CachingHandler
{
    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter()
    {
        return new ShallowEtagHeaderFilter();
    }
}
