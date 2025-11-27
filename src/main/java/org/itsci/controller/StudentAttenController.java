package org.itsci.controller;

import org.itsci.model.*;
import org.itsci.service.StudentAttenService;
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
    private StudentAttenService studentAttenService;

    @GetMapping("/manage/{enrollmentId}")
    public String authenticated_home(Model model, @PathVariable String enrollmentId) {
        Enrollment enrollment = studentAttenService.findEnrollmentById(Long.parseLong(enrollmentId));

        List<Attendance> lecAtten = new ArrayList<>();
        List<Attendance> labAtten = new ArrayList<>();
        SortedSet<Attendance> lecAttendances = studentAttenService.findAttendancesByType(enrollment, "lec");
        SortedSet<Attendance> labAttendances = studentAttenService.findAttendancesByType(enrollment, "lab");
        for (int i = 0; i < 15; i++) {
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
    private String viewAttenuation(Model model, javax.servlet.http.HttpServletRequest request,
            @PathVariable String enrollmentId, @PathVariable String attenId) {
        Enrollment enrollment = studentAttenService.findEnrollmentById(Long.parseLong(enrollmentId));
        Attendance attendance = studentAttenService.findAttendanceById(Long.parseLong(attenId));

        if (attendance == null) {
            return "redirect:/pub/student/manage/" + enrollmentId;
        }

        model.addAttribute("enrollment", enrollment);
        model.addAttribute("attendance", attendance);

        // Build image URLs for the view:
        // - If the attendance has a disk path (image1_path/image2_path), expose a URL
        // that maps to the disk-serving endpoint we provide under
        // /pub/uploads/attendance/...
        // - Otherwise fall back to the DB-served image endpoint /pub/images/{id}
        String image1Url = null;
        String image2Url = null;
        try {
            if (attendance != null) {
                if (attendance.getImage1_path() != null && !attendance.getImage1_path().isEmpty()) {
                    String p = attendance.getImage1_path().replace(java.io.File.separatorChar, '/');
                    image1Url = request.getContextPath() + "/pub/uploads/attendance/" + p;
                }

                if (attendance.getImage2_path() != null && !attendance.getImage2_path().isEmpty()) {
                    String p2 = attendance.getImage2_path().replace(java.io.File.separatorChar, '/');
                    image2Url = request.getContextPath() + "/pub/uploads/attendance/" + p2;
                }
            }
        } catch (Exception ex) {
            // non-fatal: leave URLs null if anything goes wrong
        }

        model.addAttribute("image1Url", image1Url);
        model.addAttribute("image2Url", image2Url);

        return "view-each-student-atten";
    }

    private String attentionPage(Model model, long courseId, long sectionId, int userSelectedWeek) {
        Course course = studentAttenService.findCourseById(courseId);
        List<Enrollment> enrollments = studentAttenService.findEnrollmentBySectionId(sectionId);

        Section section = null;
        for (Section sec : course.getSections()) {
            if (sec.getId() == sectionId) {
                section = sec;
                break;
            }
        }

        assert section != null;

        long currentWeek = Math.max(0, DateUtils.getCurrentWeekSemester(course));
        long displayWeek = Math.min(currentWeek, 15);
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
        model.addAttribute("sectionId", sectionId);
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
            SortedSet<Attendance> lecAttendances = studentAttenService.findAttendancesByType(enrollment, "lec");
            SortedSet<Attendance> labAttendances = studentAttenService.findAttendancesByType(enrollment, "lab");
            for (int i = 0; i < 15; i++) {
                try {
                    boolean found = false;
                    for (Attendance att : lecAttendances) {
                        if (att.getWeekNo() == i) {
                            attenData.getAttenLec()[i] = att;
                            found = true;
                            if (i == displayWeek) {
                                switch (att.getStatus()) {
                                    case ATTENDED:
                                        attendedCountLec++;
                                        break;
                                    case LATE:
                                        lateCountLec++;
                                        break;
                                    case LETTERS:
                                        letterCountLec++;
                                        break;
                                    case ABSENT:
                                        absentCountLec++;
                                        break;
                                    case NA:
                                        // Do nothing for NA status
                                        break;
                                }
                            }
                            break;
                        }
                    }
                    if (!found) {
                        attenData.getAttenLec()[i] = new Attendance();
                        attenData.getAttenLec()[i].setStatus(EAttendanceStatus.ABSENT);
                        absentCountLec = i == displayWeek ? absentCountLec + 1 : absentCountLec;
                    }
                } catch (Exception ex) {
                    attenData.getAttenLec()[i] = new Attendance();
                    attenData.getAttenLec()[i].setStatus(EAttendanceStatus.ABSENT);
                    absentCountLec = i == displayWeek ? absentCountLec + 1 : absentCountLec;
                }

                try {
                    boolean found = false;
                    for (Attendance att : labAttendances) {
                        if (att.getWeekNo() == i) {
                            attenData.getAttenLab()[i] = att;
                            found = true;
                            if (i == displayWeek) {
                                switch (att.getStatus()) {
                                    case ATTENDED:
                                        attendedCountLab++;
                                        break;
                                    case LATE:
                                        lateCountLab++;
                                        break;
                                    case LETTERS:
                                        letterCountLab++;
                                        break;
                                    case ABSENT:
                                        absentCountLab++;
                                        break;
                                    case NA:
                                        // Do nothing for NA status
                                        break;
                                }
                            }
                            break;
                        }
                    }
                    if (!found) {
                        attenData.getAttenLab()[i] = new Attendance();
                        attenData.getAttenLab()[i].setStatus(EAttendanceStatus.ABSENT);
                        absentCountLab = i == displayWeek ? absentCountLab + 1 : absentCountLab;
                    }
                } catch (Exception ex) {
                    attenData.getAttenLab()[i] = new Attendance();
                    attenData.getAttenLab()[i].setStatus(EAttendanceStatus.ABSENT);
                    absentCountLab = i == displayWeek ? absentCountLab + 1 : absentCountLab;
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

    @GetMapping("/atten/{courseId}/{sectionId}")
    public String attentionPage(Model model, @PathVariable long courseId, @PathVariable long sectionId) {
        return attentionPage(model, courseId, sectionId, -1);
    }

    @GetMapping("/atten/{courseId}/{sectionId}/{week}")
    public String attentionPage(Model model, @PathVariable long courseId, @PathVariable long sectionId,
            @PathVariable String week) {
        return attentionPage(model, courseId, sectionId, Integer.parseInt(week));
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
            @RequestParam(value = "latitude", required = false) String latitude,
            @RequestParam(value = "longitude", required = false) String longitude,
            @RequestParam("week") String week,
            @RequestParam(value = "image1", required = false) MultipartFile image1,
            @RequestParam(value = "image2", required = false) MultipartFile image2) throws IOException {
        // Save uploaded images to server storage and store file paths in Attendance
        // Upload directory can be configured via system property
        // 'attendance.upload.dir'
        String uploadDir = System.getProperty("attendance.upload.dir", "uploads/attendance");
        java.io.File uploadFolder = new java.io.File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }
        Enrollment enrollment = studentAttenService.findEnrollmentById(Long.parseLong(enrollmentId));
        Attendance attendance = null;

        SortedSet<Attendance> lecAttendances = studentAttenService.findAttendancesByType(enrollment, "lec");
        SortedSet<Attendance> labAttendances = studentAttenService.findAttendancesByType(enrollment, "lab");

        enrollment.setLecAtten(lecAttendances);
        enrollment.setLabAtten(labAttendances);

        if ("lec".equals(type)) {
            EAttendanceStatus status = DateUtils.checkAttendanceStatus(enrollment.getSection().getStartLectureTime(),
                    enrollment.getSection().getEndLectureTime());
            if (status == EAttendanceStatus.ATTENDED || status == EAttendanceStatus.LATE) {
                attendance = this.findAttendanceByWeek(lecAttendances, week);
                if (attendance == null) {
                    attendance = new Attendance();
                    attendance.setWeekNo(Integer.parseInt(week));
                    lecAttendances.add(attendance);
                }
                try {
                    if (latitude != null && !latitude.isEmpty()) {
                        attendance.setLatitude(Double.parseDouble(latitude));
                    }
                } catch (Exception ex) {
                    // ignore parse error
                }
                try {
                    if (longitude != null && !longitude.isEmpty()) {
                        attendance.setLongitude(Double.parseDouble(longitude));
                    }
                } catch (Exception ex) {
                    // ignore parse error
                }
                attendance.setStatus(status);

                // Save uploaded files (if any)
                if (image1 != null && !image1.isEmpty()) {
                    String saved = saveMultipartFile(image1, uploadFolder);
                    if (saved != null) {
                        attendance.setImage1_path(saved);
                    }
                }
                if (image2 != null && !image2.isEmpty()) {
                    String saved = saveMultipartFile(image2, uploadFolder);
                    if (saved != null) {
                        attendance.setImage2_path(saved);
                    }
                }

                studentAttenService.saveEnrollment(enrollment);
            }
        } else if ("lab".equals(type)) {
            EAttendanceStatus status = DateUtils.checkAttendanceStatus(enrollment.getSection().getStartLabTime(),
                    enrollment.getSection().getEndLabTime());
            if (status == EAttendanceStatus.ATTENDED || status == EAttendanceStatus.LATE) {
                attendance = this.findAttendanceByWeek(labAttendances, week);
                if (attendance == null) {
                    attendance = new Attendance();
                    attendance.setWeekNo(Integer.parseInt(week));
                    labAttendances.add(attendance);
                }

                double iLatitude = 0.0;
                double iLongitude = 0.0;

                try {
                    if (latitude != null && !latitude.isEmpty()) {
                        iLatitude = Double.parseDouble(latitude);
                    }
                    if (longitude != null && !longitude.isEmpty()) {
                        iLongitude = Double.parseDouble(longitude);
                    }
                } catch (Exception e) {
                    // ignore parse errors
                }

                attendance.setLatitude(iLatitude);
                attendance.setLongitude(iLongitude);
                attendance.setStatus(status);
                if (image1 != null && !image1.isEmpty()) {
                    String saved = saveMultipartFile(image1, uploadFolder);
                    if (saved != null) {
                        attendance.setImage1_path(saved);
                    }
                }
                if (image2 != null && !image2.isEmpty()) {
                    String saved = saveMultipartFile(image2, uploadFolder);
                    if (saved != null) {
                        attendance.setImage2_path(saved);
                    }
                }

                studentAttenService.saveEnrollment(enrollment);
            }
        }

        return "redirect:/pub/student/atten/" + enrollment.getSection().getCourse().getId() + "/"
                + enrollment.getSection().getId();
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

    private String saveMultipartFile(MultipartFile file, java.io.File uploadFolder) {
        if (file == null || file.isEmpty())
            return null;
        try {
            String original = file.getOriginalFilename();
            String ext = "";
            if (original != null && original.contains(".")) {
                ext = original.substring(original.lastIndexOf('.'));
            }
            String filename = System.currentTimeMillis() + "_" + java.util.UUID.randomUUID() + ext;
            // create subfolder by year/month/day (year first, then month, then day)
            java.time.LocalDate now = java.time.LocalDate.now();
            String day = String.format("%02d", now.getDayOfMonth());
            String month = String.format("%02d", now.getMonthValue());
            String year = String.valueOf(now.getYear());
            String datePath = year + java.io.File.separator + month + java.io.File.separator + day;
            java.io.File datedFolder = new java.io.File(uploadFolder, datePath);
            if (!datedFolder.exists()) {
                datedFolder.mkdirs();
            }
            java.io.File dest = new java.io.File(datedFolder, filename);
            file.transferTo(dest);
            // return path relative to uploadFolder (e.g. "dd/MM/yyyy/filename")
            return datePath + java.io.File.separator + filename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
