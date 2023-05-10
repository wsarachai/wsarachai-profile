package org.itsci.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private static final Logger logging = Logger.getLogger(HomeController.class);

//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }

    @GetMapping("/manage")
    public String authenticated_home() {
        return "manage";
    }

    @GetMapping("/calendar")
    public String calendar() {
        return "calendar";
    }
}
