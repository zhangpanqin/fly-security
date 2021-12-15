package com.mflyyou.security.jwt;

import com.mflyyou.security.authentication.UsernamePasswordAuthentication;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login",
            "POST");

    private String usernameParameter = "username";
    private String passwordParameter = "password";

    public JWTLoginFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
        this.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter(this.usernameParameter);
        username = Optional.ofNullable(username)
                .map(String::trim)
                .orElse("");
        String password = request.getParameter(this.passwordParameter);
        password = Optional.ofNullable(password)
                .orElse("");

        // 在这里可以处理多种 Authentication,比如说手机验证码登录,第三方集成登录,账号(手机,账号,邮箱)密码
        UsernamePasswordAuthentication authRequest = new UsernamePasswordAuthentication(username, password, null);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    static class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"code\":200}");
        }
    }

    static class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"code\":400}");
        }
    }


}
