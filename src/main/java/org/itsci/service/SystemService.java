package org.itsci.service;

import org.itsci.model.*;

import java.util.List;

public interface SystemService {
    Course getCourseById(long id);

    Section findSectionById(long id);

    List<Student> findStudent(String term);

    List<Section> findAllSection();

    Authority getAuthority(EAuthorityType role);

    void saveStudent(Student stu);

    void saveEnrollment(Enrollment enrollment);

    Student findStudentByStudentId(String studentId);

    void saveOrUpdateStudent(Student stu);

    boolean isStudentEnrollment(Student student, Section section);

    List<Subject> findAllSubject();

    List<Curriculum> findAllCurriculum();
}
