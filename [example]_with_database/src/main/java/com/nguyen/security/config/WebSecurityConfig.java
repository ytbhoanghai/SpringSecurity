package com.nguyen.security.config;

import com.nguyen.security.interceptor.CustomAccessDeniedHandler;
import com.nguyen.security.interceptor.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sun.security.util.Password;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private AuthenticationSuccessHandler successHandler;
    private AccessDeniedHandler accessDeniedHandler;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService,
                             AuthenticationSuccessHandler successHandler,
                             AccessDeniedHandler accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).
                passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                    antMatchers("/", "/index").permitAll().
                    antMatchers("/admin").access("hasRole('ADMIN') or hasRole('ROOT')").
                    antMatchers("/root").access("hasRole('ROOT')").
                    anyRequest().authenticated().
                and().
                    formLogin().successHandler(successHandler).
                and().
                    logout().logoutUrl("/logout").logoutSuccessUrl("/index").clearAuthentication(true).
                and().
                    exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
}
