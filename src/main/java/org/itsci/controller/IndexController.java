package org.itsci.controller;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
public class IndexController {

    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    private UserService<Teacher> userService;

    @GetMapping("/")
    public String index(Authentication authentication, Model model, HttpServletRequest request) {
        UserDetailBean userDetailBean = null;
        if (authentication != null) {
            userDetailBean = (UserDetailBean) authentication.getPrincipal();
            log.info("User " + userDetailBean.getUsername() + " is accessing the system");
        }

        Teacher teacher = userService.getUser(1l, Teacher.class);

        assert teacher != null;

        ArrayList<Course> courses = new ArrayList<>(teacher.getCourses());
        Collections.sort(courses);

        model.addAttribute("courses", courses);
        return "index";
    }
}
