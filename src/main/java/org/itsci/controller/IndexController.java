package org.itsci.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private static final Logger logging = Logger.getLogger(IndexController.class);

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Spring MVC Thymeleaf Hello World Example!!");
        return "index";
    }
}
