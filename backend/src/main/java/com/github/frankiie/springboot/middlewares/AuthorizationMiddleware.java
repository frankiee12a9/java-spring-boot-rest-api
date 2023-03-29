package com.github.frankiie.springboot.middlewares;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.github.frankiie.springboot.domains.session.service.SessionService.authorize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(1)
public class AuthorizationMiddleware extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationMiddleware.class);
    private Map<Integer, String> requestDict = new HashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter) throws ServletException, IOException {
        LOGGER.info("> doFilterInternal invoked...");
        requestDict.put(0, request.getAuthType());
        requestDict.put(1, request.getProtocol());
        requestDict.put(2, request.getLocalName());
        requestDict.put(3, request.getContextPath());
        LOGGER.info("> request: " + requestDict);
        
        authorize(request, response);

        filter.doFilter(request, response);
    }
}