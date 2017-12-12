package ru.vmsystems.template.application.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Integer.MIN_VALUE)
public class CORSFilter extends OncePerRequestFilter {

    private static final String ORIGIN = "Origin";

    private static final Logger LOG = LoggerFactory.getLogger(CORSFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String allowOrigin = (request.getHeader(ORIGIN) == null) ? "*" : request.getHeader(ORIGIN);
        LOG.info("allowOrigin: {}", allowOrigin);

        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", allowOrigin);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "authorization, content-type, xsrf-token, Origin, X-Requested-With, Accept, api_key, X-XSRF-TOKEN, X-CSRF-TOKEN");
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token, X-XSRF-TOKEN, X-CSRF-TOKEN");

        if ("OPTIONS".equals(request.getMethod())) {
            LOG.info("-- options filter --");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

