package com.nguyen.security.interceptor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component(value = "authenticationSuccessHandler")
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request,
                          HttpServletResponse response,
                          Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) { return; }

        redirectStrategy.sendRedirect(request, response, determineTargetUrl(request, response, authentication));
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            switch (grantedAuthority.getAuthority()) {
                case "ROLE_ADMIN":
                    return request.getContextPath() + "/admin";
                case "ROLE_GUEST":
                    return request.getContextPath() + "/index";
                case "ROLE_ROOT":
                    return request.getContextPath() + "/root";
            }
        }
        throw new IllegalStateException("Not found role for user " + authentication.getName());
    }
}
