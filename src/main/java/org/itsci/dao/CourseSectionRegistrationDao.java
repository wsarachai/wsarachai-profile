package org.itsci.dao;

import org.itsci.model.CourseSectionRegistration;

import java.util.List;

public interface CourseSectionRegistrationDao {
    List<CourseSectionRegistration> findStudentByCourseSectionId(Long courseSectionId);
}
