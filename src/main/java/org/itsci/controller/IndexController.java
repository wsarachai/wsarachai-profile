package org.itsci.controller;

import org.apache.log4j.Logger;
import org.itsci.model.Course;
import org.itsci.model.Teacher;
import org.itsci.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;

@Controller
public class IndexController {
    private static final Logger logging = Logger.getLogger(IndexController.class);

    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    private UserService<Teacher> userService;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Teacher teacher = null;

        if (session != null) {
            teacher = (Teacher) session.getAttribute("teacher");
        }

        if (teacher == null) {
            teacher = userService.getUser(1l, Teacher.class);
        }

        assert teacher != null;

        ArrayList<Course> courses = new ArrayList<>(teacher.getCourses());
        Collections.sort(courses);

        model.addAttribute("courses", courses);
        return "index";
    }
}
