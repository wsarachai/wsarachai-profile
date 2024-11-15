package org.itsci.controller;
import org.itsci.controller.bean.UserDetailBean;
import org.itsci.controller.bean.SemesterBean;
import org.itsci.model.Course;
import org.itsci.model.Teacher;
import org.itsci.model.TeacherCourse;
import org.itsci.service.AttenConfigService;
import org.itsci.service.CourseService;
import org.itsci.service.UserService;
import org.itsci.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService<Teacher> userService;

    @Autowired
    private AttenConfigService attenConfigService;

    private String doIndex(Authentication authentication, Model model, String year, String term) {

        log.info("[doIndex] year={}, term={}", year, term);

        UserDetailBean userDetailBean = null;
        if (authentication != null) {
            userDetailBean = (UserDetailBean) authentication.getPrincipal();
            log.info("User " + userDetailBean.getUsername() + " is accessing the system");
        }

        Teacher teacher = userService.getUser(1L, Teacher.class);

        assert teacher != null;
        String semester = term + "/" + year;

        ArrayList<TeacherCourse> teacherCourses = new ArrayList<>(courseService.listCourseByTeacherAndSemester(teacher, semester));
        Collections.sort(teacherCourses);

        List<Course> courses = new ArrayList<>();
        for (TeacherCourse tc : teacherCourses) {
            courses.add(tc.getCourse());
        }

        int yearMin = attenConfigService.getYearMin();
        int yearMax = attenConfigService.getYearMax();

        SemesterBean semesterBean = new SemesterBean(year, term);

        model.addAttribute("year_min", yearMin);
        model.addAttribute("year_max", yearMax);
        model.addAttribute("semesterBean", semesterBean);
        model.addAttribute("courses", courses);

        return "index";
    }

    @PostMapping("/semester")
    public String index2(Authentication authentication, Model model, @ModelAttribute SemesterBean semesterBean) {
        return doIndex(authentication, model, semesterBean.getYear(), semesterBean.getTerm());
    }

    @GetMapping("/semester")
    public String index3(Authentication authentication, Model model) {
        return doIndex(authentication, model, DateUtils.getCurrentSemesterYear(), DateUtils.getCurrentSemesterTerm());
    }

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        return doIndex(authentication, model, DateUtils.getCurrentSemesterYear(), DateUtils.getCurrentSemesterTerm());
    }
}
