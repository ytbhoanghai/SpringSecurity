package com.nguyen.security.controller;

import com.nguyen.security.UserService;
import com.nguyen.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/")
public class BaseController {

    private UserService userService;

    @Autowired
    public BaseController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "index"})
    public String listAllUser(Model model) {
        model.addAttribute("listUser", userService.findAllUsers());
        return "index";
    }

    @GetMapping(value = "edit-user-{id}")
    public String editUser(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @GetMapping(value = "update-user-{id}")
    public String updateUser(User user, Model model) {
        userService.updateUser(user);
        model.addAttribute("user", userService.findById(user.getId()));
        return "edit";
    }

    @GetMapping(value = "delete-user-{id}")
    public String deleteUser(@PathVariable Integer id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("listUser", userService.findAllUsers());
        return "index";
    }

    @GetMapping(value = "access_denied")
    public String doGetAccessDenied() {
        return "access_denied";
    }

    @GetMapping(value = "info")
    public String doGetInfo(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        return "info";
    }
}
