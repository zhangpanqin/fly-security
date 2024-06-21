package com.mflyyou.security.jwt;


import com.mflyyou.security.authentication.AuthorizationForAuthentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class JWTAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String bearerToken = httpServletRequest.getHeader("user");

        if (StringUtils.isNoneBlank(bearerToken)) {
            SecurityContextHolder.getContext().setAuthentication(new AuthorizationForAuthentication(List.of(), bearerToken));
        }
        chain.doFilter(request, response);
    }
}