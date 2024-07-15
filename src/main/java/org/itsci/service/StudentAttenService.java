package org.itsci.service;

import org.itsci.model.*;

import java.util.List;
import java.util.Set;

public interface StudentAttenService {
    Course findCourseById(Long id);

    List<Enrollment> findEnrollmentBySectionId(Long id);

    Student findStudentById(String studentId);

    void saveEnrollment(Enrollment enrollment);

    Enrollment findSectionBySectionId(String secionId);

    void saveAttendance(Attendance attendance);

    Set<Authority> findAllAuthorities();

    void saveAuthority(Authority authority1);

    List<Subject> findAllSubjects();

    Curriculum findCurriculumById(Long i);

    void saveSubject(Subject subject);

    List<Room> findAllRooms();

    void saveRoom(Room room1);

    List<Curriculum> findAllCurriculums();

    List<User> findAllUsers();

    void saveUser(User user);

    List<Course> findAllCourse();

    Subject findSubjectById(long l);

    void saveCourse(Course course1);

    Room findRoomById(long id);

    Authority findByRoleName(EAuthorityType role);

    void saveCurriculum(Curriculum curriculum1);

    Teacher findTeacherById(Long id);

    void updateUser(User user);

    Section findSectionById(long id);

    void updateCourse(Course course);

    Enrollment findEnrollmentById(long id);

    Attendance findAttendanceById(long id);

    void updateEnrollment(Enrollment enrollment);
}
