package org.itsci.controller;

import org.apache.log4j.Logger;
import org.itsci.controller.bean.UserDetailBean;
import org.itsci.model.Course;
import org.itsci.model.Teacher;
import org.itsci.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
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
    public String index(Authentication authentication, Model model, HttpServletRequest request) {
        UserDetailBean userDetailBean = null;
        if (authentication != null) {
            userDetailBean = (UserDetailBean) authentication.getPrincipal();
            logging.info("User " + userDetailBean.getUsername() + " is accessing the system");
        } else {
            logging.info("User is not authenticated");
        }

        Teacher teacher = userService.getUser(1l, Teacher.class);

        assert teacher != null;

        ArrayList<Course> courses = new ArrayList<>(teacher.getCourses());
        Collections.sort(courses);

        model.addAttribute("courses", courses);
        return "index";
    }
}
