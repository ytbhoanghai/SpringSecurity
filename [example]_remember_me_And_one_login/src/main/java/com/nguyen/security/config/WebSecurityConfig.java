package com.nguyen.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private DataSource dataSource;
    private AuthenticationSuccessHandler successHandler;
    private AccessDeniedHandler accessDeniedHandler;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService,
                             DataSource dataSource,
                             AuthenticationSuccessHandler successHandler,
                             AccessDeniedHandler accessDeniedHandler) {
        this.userDetailsService     = userDetailsService;
        this.dataSource             = dataSource;
        this.successHandler         = successHandler;
        this.accessDeniedHandler    = accessDeniedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                    antMatchers("/", "/index", "/login").permitAll().
                    antMatchers("/admin**").access("hasRole('ADMIN') or hasRole('ROOT')").
                    antMatchers("/root**").access("hasRole('ROOT')").
                    anyRequest().authenticated().
                and().
                    formLogin().
                        loginPage("/login").
                            usernameParameter("username").
                            passwordParameter("password").
                        successHandler(successHandler).
                and().
                    logout().
                        logoutUrl("/logout").
                        logoutSuccessUrl("/index").
                        clearAuthentication(true).
                        deleteCookies("JSESSIONID").
                        invalidateHttpSession(true).
                and().
                    exceptionHandling().accessDeniedHandler(accessDeniedHandler).
                and().
                    rememberMe().
                        rememberMeParameter("remember-me").
                        tokenRepository(persistentTokenRepository()).
                        tokenValiditySeconds(86400).
                and().
                    csrf();
    }

    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
