package com.example.vehicle.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(filterName = "MyFilter",urlPatterns = "/*")
public class HeaderFilter implements Filter
{

    private static final String PARAMS_SEPARATE = ", ";
    private static Logger logger= LoggerFactory.getLogger(HeaderFilter.class);



    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("execute filter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = ((HttpServletResponse) servletResponse);

        httpServletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, httpServletRequest.getHeader(HttpHeaders.ORIGIN));

        httpServletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Authorization,"
                + " Content-Type, Accept, Connection, User-Agent, Cookie, token");
        httpServletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "7200");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(httpServletRequest.getMethod())) {
            httpServletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, httpServletRequest.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD));
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());

        } else {
            httpServletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, httpServletRequest.getMethod());
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    private String getHeaders(HttpServletRequest httpServletRequest) {
        StringBuilder params = new StringBuilder();
        String accessControlRequestHeaders = httpServletRequest.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
        if (!(accessControlRequestHeaders == null || accessControlRequestHeaders.isEmpty())) {
            params.append(accessControlRequestHeaders).append(PARAMS_SEPARATE);
        }

        Enumeration<String> names = httpServletRequest.getHeaderNames();
        while (names.hasMoreElements()) {
            params.append(names.nextElement()).append(PARAMS_SEPARATE);
        }
        params.setLength(params.length() - PARAMS_SEPARATE.length());
        return params.toString();
    }

    @Override
    public void destroy() {

    }
}
