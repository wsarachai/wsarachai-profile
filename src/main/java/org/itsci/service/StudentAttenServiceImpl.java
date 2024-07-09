package org.itsci.service;

import org.itsci.dao.CourseDao;
import org.itsci.dao.CourseSectionDao;
import org.itsci.dao.CourseSectionRegistrationDao;
import org.itsci.dao.UserDao;
import org.itsci.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentAttenServiceImpl implements StudentAttenService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private CourseSectionDao courseSectionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseSectionRegistrationDao courseSectionRegistrationDao;

    @Override
    @Transactional
    public Course getCourse(Long id) {
        return courseDao.getCourse(id);
    }

    @Override
    @Transactional
    public CourseSection getCourseSection(Long id) {
        return courseSectionDao.getCourseSection(id);
    }

    @Override
    @Transactional
    public List<CourseSectionRegistration> findStudentByCourseSectionId(Long id) {
        return courseSectionRegistrationDao.findStudentByCourseSectionId(id);
    }

    @Override
    @Transactional
    public Student getStudent(String studentId) {
        return (Student) userDao.getUser(Long.valueOf(studentId), Student.class);
    }

    @Override
    @Transactional
    public void saveCourseSectionRegistration(CourseSectionRegistration courseSectionRegistration) {
        courseSectionRegistrationDao.save(courseSectionRegistration);
    }

    @Override
    public CourseSectionRegistration findCourseSectionRegistrationBySectionId(String secionId) {
        return null;
    }

    @Override
    public void saveAttendance(Attendance attendance) {

    }
}
