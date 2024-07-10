package org.itsci.dao;

import org.itsci.model.Enrollment;

import java.util.List;

public interface EnrollmentDao {
    List<Enrollment> findStudentByCourseSectionId(Long courseSectionId);

    void save(Enrollment courseSectionRegistration);
}
