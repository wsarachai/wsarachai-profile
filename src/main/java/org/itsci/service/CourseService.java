package org.itsci.service;

import org.itsci.model.Teacher;
import org.itsci.model.TeacherCourse;

import java.util.List;

public interface CourseService {
    List<TeacherCourse> listCourseByTeacher(Teacher teacher);
    List<TeacherCourse> listCourseByTeacherAndSemester(Teacher teacher, String semester);
}
