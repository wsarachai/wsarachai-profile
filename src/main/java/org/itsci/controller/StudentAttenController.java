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
    private UserService<Student> userService;

    @Autowired
    private StudentAttenService studentAttenService;

    @GetMapping("/manage/{enrollmentId}")
    public String authenticated_home(Model model, @PathVariable String enrollmentId) {
            Enrollment enrollment = studentAttenService.findEnrollmentById(Long.parseLong(enrollmentId));
            List<Attendance> lecAtten = new ArrayList<>();
            List<Attendance> labAtten = new ArrayList<>();
            List<Attendance> lecAttendances = new ArrayList<>(enrollment.getLecAtten());
            List<Attendance> labAttendances = new ArrayList<>(enrollment.getLabAtten());
            for (int i=0; i<15; i++) {
                try {
                    boolean found = false;
                    for (Attendance att : lecAttendances) {
                        if (att.getWeekNo() == i) {
                            lecAtten.add(att);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        Attendance newAtt = new Attendance();
                        newAtt.setWeekNo(i);
                        newAtt.setStatus(EAttendanceStatus.ABSENT);
                        lecAtten.add(newAtt);
                    }
                } catch (Exception ex) {
                    Attendance newAtt = new Attendance();
                    newAtt.setWeekNo(i);
                    newAtt.setStatus(EAttendanceStatus.ABSENT);
                    lecAtten.add(newAtt);
                }

                try {
                    boolean found = false;
                    for (Attendance att : labAttendances) {
                        if (att.getWeekNo() == i) {
                            labAtten.add(att);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        Attendance newAtt = new Attendance();
                        newAtt.setWeekNo(i);
                        newAtt.setStatus(EAttendanceStatus.ABSENT);
                        labAtten.add(newAtt);
                    }
                } catch (Exception ex) {
                    Attendance newAtt = new Attendance();
                    newAtt.setWeekNo(i);
                    newAtt.setStatus(EAttendanceStatus.ABSENT);
                    labAtten.add(newAtt);
                }

                model.addAttribute("enrollment", enrollment);
                model.addAttribute("lecAtten", lecAtten);
                model.addAttribute("labAtten", labAtten);
        }
        return "list-each-students-atten";
    }

    @GetMapping("/view/{enrollmentId}/{attenId}")
    private String viewAttenuation(Model model, @PathVariable String enrollmentId, @PathVariable String attenId) {
        Enrollment enrollment = studentAttenService.findEnrollmentById(Long.parseLong(enrollmentId));
        Attendance attendance = studentAttenService.findAttendanceById(Long.parseLong(attenId));

        if (attendance == null) {
            return "redirect:/pub/student/manage/" + enrollmentId;
        }

        model.addAttribute("enrollment", enrollment);
        model.addAttribute("attendance", attendance);

        return "view-each-student-atten";
    }

    private String attentionPage(Model model, String courseId, String secionId, int userSelectedWeek) {
        Course course = studentAttenService.findCourseById(Long.parseLong(courseId));
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
        boolean allowAttend = false;

        // If user selected a week, display that week
        if (userSelectedWeek >= 0) {
            displayWeek = userSelectedWeek;
        }

        // If user display week is the current week, allow attendance
        if (displayWeek == currentWeek) {
            allowAttend = true;
        }

        // Check if user is allowed to attend
        boolean isInTimeForLecAttend = allowAttend && DateUtils.isInTimeForLecAttend(section);
        boolean isInTimeForLabAttend = allowAttend && DateUtils.isInTimeForLabAttend(section);

        model.addAttribute("isInTimeForLecAttend", isInTimeForLecAttend);
        model.addAttribute("isInTimeForLabAttend", isInTimeForLabAttend);
        model.addAttribute("currentWeek", currentWeek);
        model.addAttribute("displayWeek", displayWeek);
        model.addAttribute("courseId", courseId);
        model.addAttribute("secionId", secionId);
        model.addAttribute("course", course);
        model.addAttribute("section", section);

        int total = enrollments.size();
        int absentCountLec = 0;
        int absentCountLab = 0;
        int attendedCountLec = 0;
        int attendedCountLab = 0;
        int lateCountLec = 0;
        int lateCountLab = 0;
        int letterCountLec = 0;
        int letterCountLab = 0;

        List<AttenData> attendanceList = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            AttenData attenData = new AttenData(enrollment);
            List<Attendance> lecAttendances = new ArrayList<>(enrollment.getLecAtten());
            List<Attendance> labAttendances = new ArrayList<>(enrollment.getLabAtten());
            for (int i=0; i<15; i++) {
                try {
                    boolean found = false;
                    for (Attendance att : lecAttendances) {
                        if (att.getWeekNo() == i) {
                            attenData.getAttenLec()[i] = att.getStatus();
                            found = true;
                            switch(att.getStatus()) {
                                case ATTENDED:
                                    attendedCountLec = i == displayWeek ? attendedCountLec+1 : attendedCountLec;
                                    break;
                                case LATE:
                                    lateCountLec = i == displayWeek ? lateCountLec+1 : lateCountLec;
                                    break;
                                case LETTERS:
                                    letterCountLec = i == displayWeek ? letterCountLec+1 : letterCountLec;
                                    break;
                                case ABSENT:
                                    absentCountLec = i == absentCountLec ? absentCountLec+1 : absentCountLec;
                                    break;
                            }
                            break;
                        }
                    }
                    if (!found) {
                        attenData.getAttenLec()[i] = EAttendanceStatus.ABSENT;
                        absentCountLec = i == displayWeek ? absentCountLec+1 : absentCountLec;
                    }
                } catch (Exception ex) {
                    attenData.getAttenLec()[i] = EAttendanceStatus.ABSENT;
                    absentCountLec = i == displayWeek ? absentCountLec+1 : absentCountLec;
                }

                model.addAttribute("attendedCountLec", attendedCountLec);
                model.addAttribute("lateCountLec", lateCountLec);
                model.addAttribute("letterCountLec", letterCountLec);
                model.addAttribute("absentCountLec", absentCountLec);

                try {
                    boolean found = false;
                    for (Attendance att : labAttendances) {
                        if (att.getWeekNo() == i) {
                            attenData.getAttenLab()[i] = att.getStatus();
                            found = true;
                            switch(att.getStatus()) {
                                case ATTENDED:
                                    attendedCountLab = i == displayWeek ? attendedCountLab+1 : attendedCountLab;
                                    break;
                                case LATE:
                                    lateCountLab = i == displayWeek ? lateCountLab+1 : lateCountLab;
                                    break;
                                case LETTERS:
                                    letterCountLab = i == displayWeek ? letterCountLab+1 : letterCountLab;
                                    break;
                                case ABSENT:
                                    absentCountLab = i == displayWeek ? absentCountLab+1 : absentCountLab;
                                    break;
                            }
                            break;
                        }
                    }
                    if (!found) {
                        attenData.getAttenLab()[i] = EAttendanceStatus.ABSENT;
                        absentCountLab = i == displayWeek ? absentCountLab+1 : absentCountLab;
                    }
                } catch (Exception ex) {
                    attenData.getAttenLab()[i] = EAttendanceStatus.ABSENT;
                    absentCountLab = i == displayWeek ? absentCountLab+1 : absentCountLab;
                }
            }

            attendanceList.add(attenData);
        }

        model.addAttribute("total", total);
        model.addAttribute("attendedCountLec", attendedCountLec);
        model.addAttribute("lateCountLec", lateCountLec);
        model.addAttribute("letterCountLec", letterCountLec);
        model.addAttribute("absentCountLec", absentCountLec);
        model.addAttribute("attendedCountLab", attendedCountLab);
        model.addAttribute("lateCountLab", lateCountLab);
        model.addAttribute("letterCountLab", letterCountLab);
        model.addAttribute("absentCountLab", absentCountLab);

        model.addAttribute("attenDatas", attendanceList);

        return "list-all-student-atten";
    }

    @GetMapping("/atten/{courseId}/{secionId}")
    public String attentionPage(Model model, @PathVariable String courseId, @PathVariable String secionId) {
        return attentionPage(model, courseId, secionId, -1);
    }

    @GetMapping("/atten/{courseId}/{secionId}/{week}")
    public String attentionPage(Model model, @PathVariable String courseId, @PathVariable String secionId, @PathVariable String week) {
        return attentionPage(model, courseId, secionId, Integer.parseInt(week));
    }

    @GetMapping("/atten/{courseId}/{enrollmentId}/{type}/{week}")
    public String attentionStudentPage(Model model,
                                       @PathVariable String courseId,
                                       @PathVariable String enrollmentId,
                                       @PathVariable String type,
                                       @PathVariable String week) {
        Course course = studentAttenService.findCourseById(Long.parseLong(courseId));
        Enrollment enrollment = studentAttenService.findEnrollmentById(Long.parseLong(enrollmentId));

        model.addAttribute("type", type);
        model.addAttribute("week", week);
        model.addAttribute("course", course);
        model.addAttribute("enrollment", enrollment);

        return "student-do-atten";
    }

    @PostMapping("/atten/doatten")
    public String doAtten(Model model,
                          @RequestParam("enrollmentId") String enrollmentId,
                          @RequestParam("type") String type,
                          @RequestParam("latitude") String latitude,
                          @RequestParam("longitude") String longitude,
                          @RequestParam("week") String week,
                          @RequestParam("image1") MultipartFile image1,
                          @RequestParam("image2") MultipartFile image2
    ) throws IOException {
        Byte[] byteObjects1 = this.convertToBytes(image1);
        Byte[] byteObjects2 = this.convertToBytes(image2);
        Enrollment enrollment = studentAttenService.findEnrollmentById(Long.parseLong(enrollmentId));
        Attendance attendance = null;

        if ("lec".equals(type)) {
            EAttendanceStatus status = DateUtils.checkAttendanceStatus(enrollment.getSection().getStartLectureTime(), enrollment.getSection().getEndLectureTime());
            if (status == EAttendanceStatus.ATTENDED || status == EAttendanceStatus.LATE) {
                attendance = this.findAttendanceByWeek(enrollment.getLecAtten(), week);
                if (attendance == null) {
                    attendance = new Attendance();
                    attendance.setWeekNo(Integer.parseInt(week));
                    enrollment.getLecAtten().add(attendance);
                }
                attendance.setLatitude(Double.parseDouble(latitude));
                attendance.setLongitude(Double.parseDouble(longitude));
                attendance.setStatus(status);
                attendance.setStudentImage(byteObjects1);
                attendance.setCodeImage(byteObjects2);
                attendance.setAttendanceTime(new Date());
                studentAttenService.saveEnrollment(enrollment);
            }
        } else if ("lab".equals(type)) {
            EAttendanceStatus status = DateUtils.checkAttendanceStatus(enrollment.getSection().getStartLabTime(), enrollment.getSection().getEndLabTime());
            if (status == EAttendanceStatus.ATTENDED || status == EAttendanceStatus.LATE) {
                attendance = this.findAttendanceByWeek(enrollment.getLabAtten(), week);
                if (attendance == null) {
                    attendance = new Attendance();
                    attendance.setWeekNo(Integer.parseInt(week));
                    enrollment.getLabAtten().add(attendance);
                }
                attendance.setLatitude(Double.parseDouble(latitude));
                attendance.setLongitude(Double.parseDouble(longitude));
                attendance.setStatus(status);
                attendance.setStudentImage(byteObjects1);
                attendance.setCodeImage(byteObjects2);
                studentAttenService.saveEnrollment(enrollment);
            }
        }

        return "redirect:/pub/student/atten/" + enrollment.getSection().getCourse().getId() + "/" + enrollment.getSection().getId();
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
