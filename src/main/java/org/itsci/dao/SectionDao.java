package org.itsci.dao;

import org.itsci.model.Section;

import java.util.List;

public interface SectionDao {
    Section findById(Long id);

    void save(Section section);

    List<Section> findAll();
}
