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
    public String index(Model model) {
        Teacher teacher = userService.getUser(1l, Teacher.class);
        String teacherName = String.format("%s%s %s", teacher.getPrename(), teacher.getFirstName(), teacher.getLastName());
        model.addAttribute("teacher_name", teacherName);

        ArrayList<Course> courses = new ArrayList<Course>(teacher.getCourses());
        Collections.sort(courses);

        model.addAttribute("courses", courses);
        return "index";
    }
}
