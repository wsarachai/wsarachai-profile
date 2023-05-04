package org.itsci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class LoginController {
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("title", messageSource.getMessage("page.login.title", null, Locale.getDefault()));
        return "login-page";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied(Model model) {
        model.addAttribute("title", messageSource.getMessage("page.access-denied.title", null, Locale.getDefault()));
        return "access-denied";
    }
}
