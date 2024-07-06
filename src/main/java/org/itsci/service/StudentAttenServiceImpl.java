package org.itsci.service;

import org.itsci.dao.CourseDao;
import org.itsci.dao.CourseSectionDao;
import org.itsci.dao.CourseSectionRegistrationDao;
import org.itsci.model.Course;
import org.itsci.model.CourseSection;
import org.itsci.model.CourseSectionRegistration;
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
    private CourseSectionRegistrationDao courseSectionRegistrationDao;

    @Override
    @Transactional
    public Course getCourse(Long id) {
        return courseDao.getUser(id);
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
}
