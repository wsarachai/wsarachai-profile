package org.itsci.controller;

import org.itsci.model.Course;
import org.itsci.model.CourseSection;
import org.itsci.model.CourseSectionRegistration;
import org.itsci.model.Teacher;
import org.itsci.service.StudentAttenService;
import org.itsci.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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

        model.addAttribute("courseId", courseId);
        model.addAttribute("secionId", secionId);
        model.addAttribute("teacher_name", teacherName);
        model.addAttribute("course", course);
        model.addAttribute("courseSection", courseSection);

        List<AttenData> attenDatas = new ArrayList<>();
        for (CourseSectionRegistration courseSectionRegistration : courseSectionRegistrations) {
            AttenData attenData = new AttenData(courseSectionRegistration.getStudent(), "0");
            JSONObject jsonObject = new JSONObject(courseSectionRegistration.getAttendance());
            JSONArray lecArray = jsonObject.getJSONArray("lec");
            JSONArray labArray = jsonObject.getJSONArray("lab");
            for (int i = 0; i < lecArray.length(); i++) {
                String lec = lecArray.getInt(i) == 1 ? "1" : "0";
                attenData.getAttenLab()[i] = lec;
            }
            for (int i = 0; i < labArray.length(); i++) {
                String lab = labArray.getInt(i) == 1 ? "1" : "0";
                attenData.getAttenLab()[i] = lab;
            }
            attenDatas.add(attenData);
        }
        model.addAttribute("attenDatas", attenDatas);

        return "student-login-page";
    }
}
