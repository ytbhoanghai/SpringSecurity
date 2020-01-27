<ol>
    <li>AccessDeniedHandler</li>
    <li>SimpleUrlAuthenticationSuccessHandler</li>
    <li>CustomAccessDeniedException extends AuthenticationException</li>
    <li>CustomUserDetailsService implements UserDetailsService</li>
    <li>CustomUserDetail implements UserDetails</li>
    <li>Method configure</li>
    <li>CSRF</li>
</ol>
<h3>AccessDeniedHandler</h3>
Xử lý ghi log và throw exception khi truy cập bị từ chối
<h3>SimpleUrlAuthenticationSuccessHandler</h3>
Xử lý redirect khi truy cập thành công. 
<ol>
    <li>@Override method determineTargetUrl(request, response, auth)</li>
    <li>Collection<? extends GrantedAuthority> grantedAuthorities = auth.getAuthorities() > lấy danh sách các quyền của user hiện tại.</li>
    <li>Throw exception IllegalStateException nếu quyền không tồn tại.</li>
</ol>
<h3>CustomAccessDeniedException extends AuthenticationException</h3>
Ngoại lệ được throw ra khi Access Denied.
<h3>CustomUserDetailsService implements UserDetailsService</h3>
Service @Override method public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException > tìm kiếm username trong db và convert sang UserDetails
<h3>CustomUserDetail implements UserDetails</h3>
Thực hiện convert User sang UserDetails (Notice: add prefix ROLE_ before role of user).
<h3>Method configure</h3>
<pre>
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
</pre>
CSRF > tránh tấn công giả mạo > thymeleaf with annotation @EnableWebSecurity<br>OR<br>
<code> input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/ </code>
