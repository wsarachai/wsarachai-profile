package org.itsci.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for test pages
 */
@Controller
@RequestMapping("/test")
public class TestController {

    /**
     * Display the IP detection test page
     * @return the test IP page
     */
    @GetMapping("/ip")
    public String testIpPage() {
        return "test-ip";
    }
}
