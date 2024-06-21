package com.mflyyou.security;

import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class CsrfController {
    @GetMapping("/csrf/info")
    public CsrfToken csrfToken(CsrfToken csrfToken) {
        return csrfToken;
    }
}
