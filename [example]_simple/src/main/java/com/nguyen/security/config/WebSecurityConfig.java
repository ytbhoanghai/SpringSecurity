package com.nguyen.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
                withUser("guest").password("{noop}123").roles("GUEST");
        auth.inMemoryAuthentication().
                withUser("admin").password("{noop}123").roles("ADMIN");
        auth.inMemoryAuthentication().
                withUser("root").password("{noop}123").roles("ROOT");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                    antMatchers("/", "/index").permitAll().
                    antMatchers("/guest/**").access("hasRole('GUEST')").
                    antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('ROOT')").
                    antMatchers("/root/**").access("hasRole('ROOT')").
                and().
                    formLogin().loginPage("/login").
                        usernameParameter("username").
                        passwordParameter("password").
                and().
                    exceptionHandling().accessDeniedPage("/access_denied");
    }
}
