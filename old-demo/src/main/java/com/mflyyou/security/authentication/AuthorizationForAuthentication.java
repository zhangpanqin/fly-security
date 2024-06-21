package com.mflyyou.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthorizationForAuthentication extends AbstractAuthenticationToken {


    private final String username;

    public AuthorizationForAuthentication(Collection<? extends GrantedAuthority> authorities, String username) {
        super(authorities);
        this.username = username;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }
}