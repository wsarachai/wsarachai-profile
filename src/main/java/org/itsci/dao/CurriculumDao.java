package org.itsci.dao;

import org.itsci.model.Curriculum;

import java.util.List;

public interface CurriculumDao {
    Curriculum getCurriculumById(Long id);

    List<Curriculum> findAll();

    void save(Curriculum curriculum1);
}
