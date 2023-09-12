package com.valentinakole.lms.util;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AddResponseHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Content-Type", "application/json");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, DELETE,OPTIONS");
        chain.doFilter(request, response);
    }
}