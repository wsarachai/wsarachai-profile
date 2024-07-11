package org.itsci.service;

import org.itsci.dao.*;
import org.itsci.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class StudentAttenServiceImpl implements StudentAttenService {

    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private CurriculumDao curriculumDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private EnrollmentDao enrollmentDao;

    @Override
    @Transactional
    public Course getCourseById(Long id) {
        return courseDao.getCourseById(id);
    }

    @Override
    @Transactional
    public List<Enrollment> findEnrollmentBySectionId(Long id) {
        return enrollmentDao.findBySectionId(id);
    }

    @Override
    @Transactional
    public Student getStudent(String studentId) {
        return (Student) userDao.getById(Long.valueOf(studentId), Student.class);
    }

    @Override
    @Transactional
    public void saveEnrollment(Enrollment enrollment) {
        enrollmentDao.save(enrollment);
    }

    @Override
    @Transactional
    public Enrollment findCourseSectionRegistrationBySectionId(String secionId) {
        return null;
    }

    @Override
    @Transactional
    public void saveAttendance(Attendance attendance) {

    }

    @Override
    @Transactional
    public Set<Authority> findAllAuthorities() {
        return authorityDao.findAll();
    }

    @Override
    @Transactional
    public void saveAuthority(Authority authority) {
        authorityDao.save(authority);
    }

    @Override
    @Transactional
    public List<Subject> findAllSubjects() {
        return subjectDao.findAll();
    }

    @Override
    @Transactional
    public Curriculum getCurriculumById(Long id) {
        return curriculumDao.getCurriculumById(id);
    }

    @Override
    @Transactional
    public void saveSubject(Subject subject) {
        subjectDao.save(subject);
    }

    @Override
    @Transactional
    public List<Room> findAllRooms() {
        return roomDao.findAll();
    }

    @Override
    @Transactional
    public void saveRoom(Room room1) {
        roomDao.save(room1);
    }

    @Override
    @Transactional
    public List<Curriculum> findAllCurriculums() {
        return curriculumDao.findAll();
    }

    @Override
    @Transactional
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    @Transactional
    public Section findSectionById(long id) {
        return sectionDao.findById(id);
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {
        courseDao.update(course);
    }

    @Override
    @Transactional
    public List<Course> findAllCourse() {
        return courseDao.findAll();
    }

    @Override
    @Transactional
    public Subject getSubjectById(long l) {
        return subjectDao.getSubjectById(l);
    }

    @Override
    @Transactional
    public void saveCourse(Course course) {
        courseDao.save(course);
    }

    @Override
    @Transactional
    public Room getRoomById(long id) {
        return roomDao.getRoomById(id);
    }

    @Override
    @Transactional
    public Authority findByRoleName(EAuthorityType role) {
        return authorityDao.findByRole(role);
    }

    @Override
    @Transactional
    public void saveCurriculum(Curriculum curriculum1) {
        curriculumDao.save(curriculum1);
    }

    @Override
    @Transactional
    public Teacher findTeacherById(Long id) {
        return (Teacher) userDao.getById(id, Teacher.class);
    }
}
