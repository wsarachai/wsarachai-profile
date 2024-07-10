package org.itsci.dao;

import org.itsci.model.Teacher;
import org.itsci.model.Course;

import java.util.List;

public interface TeachingClassDao {
    List<Course> findByTeacher(Teacher teacher);
}
