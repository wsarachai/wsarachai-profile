package org.itsci.service;

import org.itsci.model.*;

import java.util.List;

public interface StudentAttenService {
    Course getCourse(Long id);
    CourseSection getCourseSection(Long id);
    List<CourseSectionRegistration> findStudentByCourseSectionId(Long id);
    Student getStudent(String studentId);
    void saveCourseSectionRegistration(CourseSectionRegistration courseSectionRegistration);
    CourseSectionRegistration findCourseSectionRegistrationBySectionId(String secionId);

    void saveAttendance(Attendance attendance);
}
