package org.itsci.dao;

import org.itsci.model.Teacher;
import org.itsci.model.TeacherCourse;

import java.util.List;

public interface TeacherCourseDao {
    List<TeacherCourse> findCourseByTeacher(Teacher teacher, String semester, String status);
}
