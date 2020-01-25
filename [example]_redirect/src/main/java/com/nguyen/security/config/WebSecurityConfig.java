package com.nguyen.security.config;

import com.nguyen.security.interceptor.CustomAccessDeniedHandler;
import com.nguyen.security.interceptor.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private AccessDeniedHandler accessDeniedHandler;
    private CustomSuccessHandler customSuccessHandler;

    public WebSecurityConfig(@Autowired CustomAccessDeniedHandler accessDeniedHandler,
                             @Autowired CustomSuccessHandler customSuccessHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.customSuccessHandler = customSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
                withUser("admin").password("{noop}admin").roles("ADMIN");
        auth.inMemoryAuthentication().
                withUser("guest").password("{noop}guest").roles("GUEST");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                    antMatchers("/index", "/").permitAll().
                    antMatchers("/admin/**").access("hasRole('ADMIN')").
                    anyRequest().authenticated().
                and().
                    formLogin().successHandler(customSuccessHandler).
                and().
                    logout().logoutUrl("/logout").logoutSuccessUrl("/index").clearAuthentication(true).
                and().
                    exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
}
