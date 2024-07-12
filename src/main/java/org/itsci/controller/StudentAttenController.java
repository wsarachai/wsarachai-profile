package org.itsci.controller;

import org.itsci.model.*;
import org.itsci.service.StudentAttenService;
import org.itsci.service.UserService;
import org.itsci.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/pub/student")
public class StudentAttenController {

    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    private UserService<Teacher> userService;

    @Autowired
    private StudentAttenService studentAttenService;

//    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    private String attentionPage(Model model, String courseId, String secionId, int week) {
        Course course = studentAttenService.getCourseById(Long.parseLong(courseId));
        List<Enrollment> enrollments = studentAttenService.findEnrollmentBySectionId(Long.parseLong(secionId));

        Section section = null;
        for (Section sec : course.getSections()) {
            if (sec.getId() == Long.parseLong(secionId)) {
                section = sec;
                break;
            }
        }

        assert section != null;

        int currentWeek = DateUtils.getCurrentWeekSemester(course);
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
        model.addAttribute("course", course);
        model.addAttribute("section", section);

        List<AttenData> attenDatas = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            AttenData attenData = new AttenData(enrollment.getStudent(), "0");
            List<Attendance> lecAttendances = new ArrayList<>(enrollment.getLecAtten());
            List<Attendance> labAttendances = new ArrayList<>(enrollment.getLabAtten());
            for (int i=0; i<15; i++) {
                try {
                    attenData.getAttenLec()[i] = lecAttendances.get(i).getStatus();
                } catch (Exception ex) {
                    attenData.getAttenLec()[i] = EAttendanceStatus.NA;
                }

                try {
                    attenData.getAttenLab()[i] = labAttendances.get(i).getStatus();
                } catch (Exception ex) {
                    attenData.getAttenLab()[i] = EAttendanceStatus.NA;
                }
            }
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
        Course course = studentAttenService.getCourseById(Long.parseLong(courseId));
        Student student = studentAttenService.getStudent(studentId);
        Section section = studentAttenService.findSectionById(Long.parseLong(secionId));

        model.addAttribute("type", type);
        model.addAttribute("week", week);
        model.addAttribute("course", course);
        model.addAttribute("student", student);
        model.addAttribute("section", section);

        return "student-atten-page";
    }

    @PostMapping("/atten/doatten")
    public String doAtten(Model model,
            @RequestParam("secionId") String secionId,
            @RequestParam("type") String type,
            @RequestParam("week") String week,
            @RequestParam("image1") MultipartFile image1,
            @RequestParam("image2") MultipartFile image2
    ) throws IOException {
        Byte[] byteObjects1 = this.convertToBytes(image1);
        Byte[] byteObjects2 = this.convertToBytes(image2);
        Enrollment csr = studentAttenService.findCourseSectionRegistrationBySectionId(secionId);
        Attendance attendance = null;
        if ("lec".equals(type)) {
            attendance = this.findAttendanceByWeek(csr.getLecAtten(), week);
        } else if ("lab".equals(type)) {
            attendance = this.findAttendanceByWeek(csr.getLabAtten(), week);
        }
        attendance.setStatus(EAttendanceStatus.ATTENDED);
        attendance.setStudentImage(byteObjects1);
        attendance.setCodeImage(byteObjects2);
        studentAttenService.saveAttendance(attendance);

//        StringBuilder fileNames = new StringBuilder();
//        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, image1.getOriginalFilename());
//        fileNames.append(image1.getOriginalFilename());
//        Files.write(fileNameAndPath, image1.getBytes());
//        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());

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
