package ru.vmsystems.template.application.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Integer.MIN_VALUE)
public class CORSFilter extends OncePerRequestFilter {

    @Value("${allowOrigin}")
    private String allowOrigin;
    private static final String ORIGIN = "Origin";

    private static final Logger LOG = LoggerFactory.getLogger(CORSFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOG.debug("remote Origin: {}. allowOrigin: {}", request.getHeader(ORIGIN), allowOrigin);

        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", allowOrigin);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "authorization, content-type, xsrf-token, Origin, X-Requested-With, Accept, api_key, X-XSRF-TOKEN, X-CSRF-TOKEN");
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token, X-XSRF-TOKEN, X-CSRF-TOKEN");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            //для swagger добавить токен
            String referer = request.getHeader("referer");
            if (referer != null && referer.contains("swagger-ui.html")) {
                Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                if (cookie != null) {
                    request = new HttpServletRequestWrapper(request) {
                        @Override
                        public String getHeader(String name) {
                            if (name.equals("X-XSRF-TOKEN")) {
                                return cookie.getValue();
                            }
                            return super.getHeader(name);
                        }
                    };
                }
            }

            filterChain.doFilter(request, response);
        }
    }
}

