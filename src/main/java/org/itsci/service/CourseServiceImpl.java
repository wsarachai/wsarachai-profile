package org.itsci.service;

import org.itsci.dao.TeacherCourseDaoImpl;
import org.itsci.model.Teacher;
import org.itsci.model.TeacherCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private TeacherCourseDaoImpl teacherCourseDao;

    @Override
    @Transactional
    public List<TeacherCourse> listCourseByTeacher(Teacher teacher) {
        return teacherCourseDao.findCourseByTeacher(teacher, null, null);
    }

    @Override
    @Transactional
    public List<TeacherCourse> listCourseByTeacherAndSemester(Teacher teacher, String semester) {
        return teacherCourseDao.findCourseByTeacher(teacher, semester, null);
    }
}
