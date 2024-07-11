package org.itsci.service;

import org.itsci.model.*;

import java.util.List;
import java.util.Set;

public interface StudentAttenService {
    Course getCourseById(Long id);

    Section getCourseSection(Long id);

    List<Enrollment> findStudentByCourseSectionId(Long id);

    Student getStudent(String studentId);

    void saveCourseSectionRegistration(Enrollment courseSectionRegistration);

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

    void saveSection(Section section1);

    Authority findByRoleName(EAuthorityType role);

    void saveCurriculum(Curriculum curriculum1);

    Teacher findTeacherById(Long id);

    void updateUser(User user);
}
