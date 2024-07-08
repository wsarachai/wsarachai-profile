package org.itsci.service;

import org.itsci.model.Course;
import org.itsci.model.CourseSection;
import org.itsci.model.CourseSectionRegistration;
import org.itsci.model.Student;

import java.util.List;

public interface StudentAttenService {
    Course getCourse(Long id);
    CourseSection getCourseSection(Long id);
    List<CourseSectionRegistration> findStudentByCourseSectionId(Long id);
    Student getStudent(String studentId);
}
