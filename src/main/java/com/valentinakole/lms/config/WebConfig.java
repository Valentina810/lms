package com.valentinakole.lms.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>( new ShallowEtagHeaderFilter());
        filterRegistrationBean.addUrlPatterns("*");
        filterRegistrationBean.setName("etagFilter");
        return filterRegistrationBean;
    }
}