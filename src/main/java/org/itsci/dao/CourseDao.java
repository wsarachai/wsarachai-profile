package org.itsci.dao;

import org.itsci.model.Course;

public interface CourseDao {
    Course getCourse(Long id);

    Course updateCourse(Course course);

    void saveCourse(Course course);

    void deleteCourse(Long id);

}
