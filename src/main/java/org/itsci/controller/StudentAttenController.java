package org.itsci.controller;

import org.itsci.model.*;
import org.itsci.service.StudentAttenService;
import org.itsci.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/student")
public class StudentAttenController {

    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    private UserService<Teacher> userService;

    @Autowired
    private StudentAttenService studentAttenService;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

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

    @GetMapping("/atten/{courseId}/{secionId}/{studentId}/{type}/{week}")
    public String attentionStudentPage(Model model,
                                       @PathVariable String courseId,
                                       @PathVariable String secionId,
                                       @PathVariable String studentId,
                                       @PathVariable String type,
                                       @PathVariable String week) {
        Teacher teacher = userService.getUser(1l, Teacher.class);
        String teacherName = String.format("%s%s %s", teacher.getPrename(), teacher.getFirstName(), teacher.getLastName());
        Course course = studentAttenService.getCourse(Long.parseLong(courseId));
        Student student = studentAttenService.getStudent(studentId);
        CourseSection courseSection = studentAttenService.getCourseSection(Long.parseLong(secionId));

        model.addAttribute("type", type);
        model.addAttribute("week", week);
        model.addAttribute("course", course);
        model.addAttribute("student", student);
        model.addAttribute("courseSection", courseSection);
        model.addAttribute("teacherName", teacherName);

        return "student-atten-page";
    }

    @PostMapping("/atten/doatten")
    public String doAtten(@RequestParam("secionId") String secionId,
                          @RequestParam("type") String type,
                          @RequestParam("week") String week
//                          @RequestParam("image1") MultipartFile image1,
//                          @RequestParam("image2") MultipartFile image2
    ) throws IOException {
//        Byte[] byteObjects1 = this.convertToBytes(image1);
//        Byte[] byteObjects2 = this.convertToBytes(image2);
        CourseSectionRegistration csr = studentAttenService.findCourseSectionRegistrationBySectionId(secionId);
        Attendance attendance = null;
        if ("lec".equals(type)) {
            attendance = this.findAttendanceByWeek(csr.getLecAtten(), week);
        } else if ("lab".equals(type)) {
            attendance = this.findAttendanceByWeek(csr.getLabAtten(), week);
        }
        attendance.setStatus(EAttendanceStatus.ATTENDED);
//        attendance.setStudentImage(byteObjects1);
//        attendance.setCodeImage(byteObjects2);
        studentAttenService.saveAttendance(attendance);
        return "index";
    }

    private Attendance findAttendanceByWeek(SortedSet<Attendance> attendances, String week) {
        Attendance attendance = null;
        for (Attendance atten : attendances) {
            if (atten.getWeekNo() == Integer.parseInt(week)) {
                attendance = atten;
            }
        }
        return attendance;
    }

    private Byte[] convertToBytes(MultipartFile file) throws IOException {
        Byte[] byteObjects = new Byte[file.getBytes().length];
        int i = 0;
        for (byte b : file.getBytes()) {
            byteObjects[i++] = b;
        }
        return byteObjects;
    }
}
