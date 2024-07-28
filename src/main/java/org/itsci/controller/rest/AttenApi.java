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
import java.util.List;
import java.util.SortedSet;

@RestController
@RequestMapping("/api/v1")
public class AttenApi {

    @Autowired
    private StudentAttenService studentAttenService;

    @CacheEvict(value = {"attendances"}, key = "#param.enrollmentId+#param.type")
    @PostMapping(value="/atten/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addMember(@RequestBody UpdateParamBean param) {
        Enrollment enrollment = studentAttenService.findEnrollmentById(Long.parseLong(param.getEnrollmentId()));

        SortedSet<Attendance> lecAttens = studentAttenService.findAttendancesByType(enrollment, "lec");
        SortedSet<Attendance> labAttens = studentAttenService.findAttendancesByType(enrollment, "lab");

        enrollment.setLecAtten(lecAttens);
        enrollment.setLabAtten(labAttens);

        if ("lec".equals(param.getType())) {
            boolean found = false;
            for (Attendance atten : lecAttens) {
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
                lecAttens.add(atten);
                studentAttenService.updateEnrollment(enrollment);
            }
        } else if ("lab".equals(param.getType())) {
            boolean found = false;
            for (Attendance atten : labAttens) {
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
                labAttens.add(atten);
                studentAttenService.updateEnrollment(enrollment);
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid type");
        }

        return ResponseEntity.ok("Update attendance successfully");
    }
}
