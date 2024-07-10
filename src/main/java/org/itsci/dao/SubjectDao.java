package org.itsci.dao;

import org.itsci.model.Subject;

import java.util.List;

public interface SubjectDao {
    List<Subject> findAll();

    void save(Subject subject);

    Subject getSubjectById(long id);
}
