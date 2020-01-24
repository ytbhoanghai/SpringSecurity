<h3>Notice</h3>
Not use lib thymeleaf-extras-springsecurity4 (error sec:authorize="hasRole('ROLE...')") update to > thymeleaf-extras-springsecurity5
<hr>
<h4>References</h4>
<ol>
    <li><a href="http://websystique.com/spring-security/spring-security-4-secure-view-layer-using-taglibs/">websystique</a></li>
    <li><a href="https://memorynotfound.com/spring-boot-spring-security-thymeleaf-form-login-example">memorynotfound</a></li>
    <li><a href="https://www.thymeleaf.org/doc/articles/springsecurity.html">thymeleaf</a></li>
</ol>
<hr>
<h4>Note</h4>
<ol>
    <li>Implement interface AccessDeniedHandler handler AccessDenied</li>
    <li>sec:authorize=hasRole('ROLE...')</li>
    <li>sec:authentication="name"</li>
    <li>sec:authentication="principal.authorities"/</li>
    <li>sec:authorize="isAuthenticated()"</li>
</ol>