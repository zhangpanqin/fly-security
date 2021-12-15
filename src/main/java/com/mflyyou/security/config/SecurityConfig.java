package com.mflyyou.security.config;

import com.mflyyou.security.JwtAccessDeniedHandler;
import com.mflyyou.security.JwtAuthenticationEntryPoint;
import com.mflyyou.security.MyAuthenticationProvider;
import com.mflyyou.security.MyAuthorizationProvider;
import com.mflyyou.security.UserModelDetailsService;
import com.mflyyou.security.jwt.JWTConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }


    @Bean
    public MyAuthenticationProvider myAuthenticationProvider() {
        return new MyAuthenticationProvider();
    }

    @Bean
    public MyAuthorizationProvider myAuthorizationProvider() {
        return new MyAuthorizationProvider();
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }

    @Bean
    public UserModelDetailsService userModelDetailsService() {
        return new UserModelDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .rememberMe().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // security 全局异常处理,
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint())
                .accessDeniedHandler(jwtAccessDeniedHandler())

                .and()
                .authorizeRequests()
                // 允许某些请求匿名访问
                .antMatchers("/login").anonymous()
                // 除以上匿名访问,别的请求都需要鉴权认证
                .anyRequest().authenticated()

                .and()
                .apply(securityConfigurerAdapter())


                // 注册到当前 AuthenticationManager
                .and()
                .authenticationProvider(myAuthenticationProvider())
                .authenticationProvider(myAuthorizationProvider())
                .userDetailsService(userModelDetailsService())
        ;
    }

    // 注册到 父AuthenticationManager 中去
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(myAuthenticationProvider());
//    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer();
    }
}
