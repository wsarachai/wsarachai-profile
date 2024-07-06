package org.itsci.controller;

import org.itsci.model.Course;
import org.itsci.model.CourseSection;
import org.itsci.model.CourseSectionRegistration;
import org.itsci.model.Teacher;
import org.itsci.service.StudentAttenService;
import org.itsci.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentAttenController {

    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    private UserService<Teacher> userService;

    @Autowired
    private StudentAttenService studentAttenService;

    @GetMapping("/atten/{courseId}/{secionId}")
    public String loginPage(Model model, @PathVariable String courseId, @PathVariable String secionId) {
        Teacher teacher = userService.getUser(1l, Teacher.class);
        String teacherName = String.format("%s%s %s", teacher.getPrename(), teacher.getFirstName(), teacher.getLastName());
        Course course = studentAttenService.getCourse(Long.parseLong(courseId));
        CourseSection courseSection = studentAttenService.getCourseSection(Long.parseLong(secionId));
        List<CourseSectionRegistration> courseSectionRegistrations = studentAttenService.findStudentByCourseSectionId(Long.parseLong(secionId));

        model.addAttribute("teacher_name", teacherName);
        model.addAttribute("course", course);
        model.addAttribute("courseSection", courseSection);
        model.addAttribute("courseSectionRegistrations", courseSectionRegistrations);

        return "student-login-page";
    }
}
