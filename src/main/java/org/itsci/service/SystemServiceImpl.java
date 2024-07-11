package org.itsci.service;

import org.itsci.dao.*;
import org.itsci.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private EnrollmentDao enrollmentDao;

    @Override
    @Transactional
    public Course getCourseById(long id) {
        return courseDao.getCourseById(id);
    }

    @Override
    @Transactional
    public Section findSectionById(long id) {
        return sectionDao.findById(id);
    }

    @Override
    @Transactional
    public List<Student> findStudent(String term) {
        return null;
    }

    @Override
    @Transactional
    public List<Section> findAllSection() {
        return sectionDao.findAll();
    }

    @Override
    @Transactional
    public Authority getAuthority(EAuthorityType role) {
        return authorityDao.findByRole(role);
    }

    @Override
    @Transactional
    public void saveStudent(Student stu) {
        userDao.save(stu);
    }

    @Override
    @Transactional
    public void saveEnrollment(Enrollment enrollment) {
        enrollmentDao.save(enrollment);
    }
}
