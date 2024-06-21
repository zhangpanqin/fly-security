package com.mflyyou.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class TestController {

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('read')")
    public String test() {
        return "hello";
    }
}
