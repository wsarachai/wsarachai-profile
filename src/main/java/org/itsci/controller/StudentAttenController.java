package org.itsci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping("/student")
public class StudentAttenController {
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/login/{secionId}")
    public String loginPage(Model model, @PathVariable String secionId) {
        System.out.println("Section: " + secionId);
        model.addAttribute("title", messageSource.getMessage("page.student.login.title", null, Locale.getDefault()));
        return "student-login-page";
    }
}
