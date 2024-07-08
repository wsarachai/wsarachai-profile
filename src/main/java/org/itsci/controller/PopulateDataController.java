package org.itsci.controller;

import org.itsci.model.Attendance;
import org.itsci.model.CourseSectionRegistration;
import org.itsci.model.EAttendanceStatus;
import org.itsci.service.StudentAttenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/populate")
public class PopulateDataController {

    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    private StudentAttenService studentAttenService;

    @GetMapping("/student")
    public String populateStudent(Model model) {

        for (long i = 1; i <= 4; i++) {
            List<CourseSectionRegistration> courseSectionRegistrations = studentAttenService.findStudentByCourseSectionId(i);
            for (CourseSectionRegistration courseSectionRegistration : courseSectionRegistrations) {
                if (courseSectionRegistration.getAttendances().size() <=0) {
                    for (int j = 1; j <= 15; j++) {
                        Attendance attendance = new Attendance();
                        attendance.setWeekNo(j);
                        attendance.setCourseSectionRegistration(courseSectionRegistration);
                        attendance.setStatus(EAttendanceStatus.NA);
                        courseSectionRegistration.getAttendances().add(attendance);
                    }
                }
                studentAttenService.saveCourseSectionRegistration(courseSectionRegistration);
            }

        }
        return "index";
    }
}
