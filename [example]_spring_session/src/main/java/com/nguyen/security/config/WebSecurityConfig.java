package com.nguyen.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
                withUser("root").password("{noop}123").roles("ROOT").
        and().
                withUser("admin").password("{noop}123").roles("ADMIN").
        and().
                withUser("guest").password("{noop}123").roles("GUEST");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                    antMatchers("/", "/index", "/login").permitAll().
                    antMatchers("/admin").access("hasRole('ADMIN') or hasRole('ROOT')").
                    antMatchers("/root").access("hasRole('ROOT')").
                    anyRequest().authenticated();
        http.
                formLogin().
                and().
                    logout().
                        invalidateHttpSession(true).
                        deleteCookies("JSESSIONID").
                        logoutSuccessUrl("/index").
                        logoutUrl("/logout").
                        clearAuthentication(true).
                and().
                    exceptionHandling().accessDeniedPage("/access_denied").
                and().
                    csrf().
                and().
                    rememberMe().
                    key("uniqueAndSecret").
                    tokenValiditySeconds(86400);

        http.
                sessionManagement().
                    sessionFixation().newSession().
                    invalidSessionUrl("/info?message=session_timeout").
                    maximumSessions(1).
                    expiredUrl("/info?message=max_session");
    }
}
