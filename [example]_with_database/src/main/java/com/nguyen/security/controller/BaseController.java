package com.nguyen.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class BaseController {

    @GetMapping(value = {"", "index"})
    public String doGetIndex() {
        return "index";
    }

    @GetMapping(value = "admin")
    public String doGetAdmin() {
        return "admin";
    }

    @GetMapping(value = "root")
    public String doGetRoot() {
        return "root";
    }
}
