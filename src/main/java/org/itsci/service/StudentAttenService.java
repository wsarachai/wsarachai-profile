package org.itsci.service;

import org.itsci.model.*;

import java.util.List;
import java.util.Set;

public interface StudentAttenService {
    Course getCourseById(Long id);

    List<Enrollment> findEnrollmentBySectionId(Long id);

    Student getStudent(String studentId);

    void saveEnrollment(Enrollment enrollment);

    Enrollment findCourseSectionRegistrationBySectionId(String secionId);

    void saveAttendance(Attendance attendance);

    Set<Authority> findAllAuthorities();

    void saveAuthority(Authority authority1);

    List<Subject> findAllSubjects();

    Curriculum getCurriculumById(Long i);

    void saveSubject(Subject subject);

    List<Room> findAllRooms();

    void saveRoom(Room room1);

    List<Curriculum> findAllCurriculums();

    List<User> findAllUsers();

    void saveUser(User user);

    List<Course> findAllCourse();

    Subject getSubjectById(long l);

    void saveCourse(Course course1);

    Room getRoomById(long id);

    Authority findByRoleName(EAuthorityType role);

    void saveCurriculum(Curriculum curriculum1);

    Teacher findTeacherById(Long id);

    void updateUser(User user);

    Section findSectionById(long id);

    void updateCourse(Course course);
}
