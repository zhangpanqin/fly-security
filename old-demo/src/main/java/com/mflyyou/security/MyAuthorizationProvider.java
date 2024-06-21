package com.mflyyou.security;

import com.mflyyou.security.authentication.AuthorizationForAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class MyAuthorizationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var principal = authentication.getPrincipal().toString();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (principal.equals("user")) {
            authorities.add(() -> "read");
        }
        var authorizationForAuthentication = new AuthorizationForAuthentication(authorities, principal.toString());
        authorizationForAuthentication.setAuthenticated(true);
        return authorizationForAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(AuthorizationForAuthentication.class);
    }
}
