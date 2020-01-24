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

    @GetMapping(value = {"", "/index"})
    public String doGetIndex() {
        return "index";
    }

    @GetMapping(value = "guest")
    public String doGetGuest(Model model) {
        model.addAttribute("username", getPrincipal());
        return "guest";
    }

    @GetMapping(value = "admin")
    public String doGetAdmin(Model model) {
        model.addAttribute("username", getPrincipal());
        return "admin";
    }

    @GetMapping(value = "root")
    public String doGetRoot(Model model) {
        model.addAttribute("username", getPrincipal());
        return "root";
    }

    @GetMapping(value = "access_denied")
    public String doGetAccessDenied() {
        return "access_denied";
    }

    @GetMapping(value = "login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        if (aut != null) {
            new SecurityContextLogoutHandler().logout(request, response, aut);
        }
        return "redirect:/login";
    }

    private String getPrincipal() {
        Object principal = SecurityContextHolder.
                getContext().
                getAuthentication().
                getPrincipal();

        if (principal != null) {
            return ((UserDetails)principal).getUsername();
        }
        return principal.toString();
    }
}
