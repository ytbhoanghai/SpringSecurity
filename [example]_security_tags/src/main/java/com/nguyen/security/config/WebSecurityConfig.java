package com.nguyen.security.config;

import com.nguyen.security.interceptor.LoggingAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private LoggingAccessDeniedHandler accessDeniedHandler;

    public WebSecurityConfig(@Autowired LoggingAccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
                withUser("guest").password("{noop}guest").roles("GUEST");
        auth.inMemoryAuthentication().
                withUser("admin").password("{noop}123").roles("ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                    antMatchers("/admin").access("hasRole('ADMIN')").
                    antMatchers("/guest").access("hasRole('GUEST') or hasRole('ADMIN')").
                    anyRequest().authenticated().
                and().
                    formLogin().
                and().
                    exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
}
