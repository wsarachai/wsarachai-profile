package org.itsci.controller.rest;

import org.itsci.model.Attendance;
import org.itsci.model.EAttendanceStatus;
import org.itsci.model.Enrollment;
import org.itsci.service.StudentAttenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class AttenApi {

    @Autowired
    private StudentAttenService studentAttenService;

    @CacheEvict(value = {"enrollments"}, allEntries = true)
    @PostMapping(value="/atten/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addMember(@RequestBody UpdateParamBean param) {
        Enrollment enrollment = studentAttenService.findEnrollmentById(Long.parseLong(param.getEnrollmentId()));

        if ("lec".equals(param.getType())) {
            boolean found = false;
            for (Attendance atten : enrollment.getLecAtten()) {
                if (atten.getWeekNo() == Integer.parseInt(param.getWeek())) {
                    atten.setStatus(EAttendanceStatus.valueOf(param.getStatus()));
                    studentAttenService.updateEnrollment(enrollment);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Attendance atten = new Attendance();
                atten.setWeekNo(Integer.parseInt(param.getWeek()));
                atten.setStatus(EAttendanceStatus.valueOf(param.getStatus()));
                atten.setLatitude(999.0);
                atten.setLongitude(999.0);
                atten.setAttendanceTime(new Date());
                enrollment.getLecAtten().add(atten);
                studentAttenService.updateEnrollment(enrollment);
            }
        } else if ("lab".equals(param.getType())) {
            boolean found = false;
            for (Attendance atten : enrollment.getLabAtten()) {
                if (atten.getWeekNo() == Integer.parseInt(param.getWeek())) {
                    atten.setStatus(EAttendanceStatus.valueOf(param.getStatus()));
                    studentAttenService.updateEnrollment(enrollment);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Attendance atten = new Attendance();
                atten.setWeekNo(Integer.parseInt(param.getWeek()));
                atten.setStatus(EAttendanceStatus.valueOf(param.getStatus()));
                atten.setLatitude(999.0);
                atten.setLongitude(999.0);
                atten.setAttendanceTime(new Date());
                enrollment.getLabAtten().add(atten);
                studentAttenService.updateEnrollment(enrollment);
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid type");
        }

        return ResponseEntity.ok("Update attendance successfully");
    }
}
