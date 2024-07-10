package org.itsci.dao;

import org.itsci.model.Course;

import java.util.List;

public interface CourseDao {
    Course getCourseById(Long id);

    Course update(Course course);

    void save(Course course);

    void delete(Long id);

    List<Course> findAll();
}
