package com.leverxblog.UnitTests;

import com.leverxblog.converters.ArticleConverter;
import com.leverxblog.converters.TagConverter;
import com.leverxblog.converters.UserConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public TagConverter tagConverter() {
        return new TagConverter();
    }

    @Bean
    UserConverter userConverter() {
        return new UserConverter();
    }
}
