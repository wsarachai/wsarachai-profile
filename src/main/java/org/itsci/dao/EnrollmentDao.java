package org.itsci.dao;

import org.itsci.model.Enrollment;

import java.util.List;

public interface EnrollmentDao {
    List<Enrollment> findBySectionId(Long sectionId);

    void save(Enrollment courseSectionRegistration);
}
