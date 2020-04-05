package com.lineate.timeconverter.utils;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *This class need to regulate access to the server
 */
@Component
public class SimpleCORSFilter implements Filter {

    /**
     * Method adds to filter access on HttpServletResponse
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Expose-Headers", "client_storage_data");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,client_storage_data");
        chain.doFilter(req, res);
    }

    /**
     * Method initializes configuration
     * @param filterConfig - configuration for filter
     */
    public void init(FilterConfig filterConfig) {
    }

    /**
     * Method for destroy
     */
    public void destroy() {
    }

}
