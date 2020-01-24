package com.nguyen.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/")
public class BaseController {

    @GetMapping(value = {"", "index"})
    public String doGetIndex(Model model) {
        return "index";
    }

    @GetMapping(value = "admin")
    public String doGetAdmin() {
        return "admin";
    }

    @GetMapping(value = "guest")
    public String doGetGuest() {
        return "guest";
    }

    @GetMapping(value = "access_denied")
    public String doGetAccessDenied() {
        return "access_denied";
    }

    @GetMapping(value = "logout")
    public String doGetLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/index";
    }
}
