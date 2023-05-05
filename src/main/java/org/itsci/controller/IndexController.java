package org.itsci.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private static final Logger logging = Logger.getLogger(IndexController.class);

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
