package org.itsci.controller;

import org.itsci.model.*;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Controller
@RequestMapping("/student")
public class StudentAttenController {

    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    private UserService<Teacher> userService;

    @Autowired
    private StudentAttenService studentAttenService;

    private int getCurrentWeekSemester(Course course) {
        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        Calendar c2 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        c1.setTime(course.getStartSemester());

        int startWeek = c1.get(Calendar.WEEK_OF_YEAR);
        int curWeek = c2.get(Calendar.WEEK_OF_YEAR);

        return curWeek-startWeek;
    }

    private String attentionPage(Model model, String courseId, String secionId, int week) {
        Teacher teacher = userService.getUser(1l, Teacher.class);
        Course course = studentAttenService.getCourse(Long.parseLong(courseId));
        String teacherName = String.format("%s%s %s", teacher.getPrename(), teacher.getFirstName(), teacher.getLastName());
        CourseSection courseSection = studentAttenService.getCourseSection(Long.parseLong(secionId));
        List<CourseSectionRegistration> courseSectionRegistrations = studentAttenService.findStudentByCourseSectionId(Long.parseLong(secionId));

        int currentWeek = getCurrentWeekSemester(course);
        int displayWeek = currentWeek;
        if (week >= 0) {
            displayWeek = week;
        }
        boolean allowAtten = false;
        if (displayWeek == currentWeek) {
            allowAtten = true;
        }

        model.addAttribute("allowAtten", allowAtten);
        model.addAttribute("currentWeek", currentWeek);
        model.addAttribute("displayWeek", displayWeek);
        model.addAttribute("courseId", courseId);
        model.addAttribute("secionId", secionId);
        model.addAttribute("teacher_name", teacherName);
        model.addAttribute("course", course);
        model.addAttribute("courseSection", courseSection);

        List<AttenData> attenDatas = new ArrayList<>();
        for (CourseSectionRegistration courseSectionRegistration : courseSectionRegistrations) {
            AttenData attenData = new AttenData(courseSectionRegistration.getStudent(), "0");
            courseSectionRegistration.getLecAtten().forEach(lecAtten -> {
                attenData.getAttenLec()[lecAtten.getWeekNo()-1] = lecAtten.getStatus().toString();
            });
            courseSectionRegistration.getLabAtten().forEach(labAtten -> {
                attenData.getAttenLab()[labAtten.getWeekNo()-1] = labAtten.getStatus().toString();
            });
            attenDatas.add(attenData);
        }
        model.addAttribute("attenDatas", attenDatas);

        return "list-student-atten-page";
    }

    @GetMapping("/atten/{courseId}/{secionId}")
    public String attentionPage(Model model, @PathVariable String courseId, @PathVariable String secionId) {
        return attentionPage(model, courseId, secionId, -1);
    }

    @GetMapping("/atten/{courseId}/{secionId}/{week}")
    public String attentionPage(Model model, @PathVariable String courseId, @PathVariable String secionId, @PathVariable String week) {
        return attentionPage(model, courseId, secionId, Integer.parseInt(week));
    }

    @GetMapping("/atten/{courseId}/{secionId}/studentId/{studentId}")
    public String attentionStudentPage(Model model, @PathVariable String courseId, @PathVariable String secionId, @PathVariable String studentId) {
        Teacher teacher = userService.getUser(1l, Teacher.class);
        String teacherName = String.format("%s%s %s", teacher.getPrename(), teacher.getFirstName(), teacher.getLastName());
        Course course = studentAttenService.getCourse(Long.parseLong(courseId));
        Student student = studentAttenService.getStudent(studentId);
        CourseSection courseSection = studentAttenService.getCourseSection(Long.parseLong(secionId));

        model.addAttribute("course", course);
        model.addAttribute("student", student);
        model.addAttribute("courseSection", courseSection);
        model.addAttribute("teacherName", teacherName);

        return "student-atten-page";
    }
}
