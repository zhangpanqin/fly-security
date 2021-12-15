package com.mflyyou.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UsernamePasswordAuthentication extends AbstractAuthenticationToken {
    private String username;

    private String password;


    public UsernamePasswordAuthentication(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
        this.password = password;
    }

    @Override
    public String getCredentials() {
        return this.password;
    }

    @Override
    public String getPrincipal() {
        return this.username;
    }
}
