package org.itsci.dao;

import org.itsci.model.Section;

public interface SectionDao {
    Section findById(Long id);

    void save(Section section);
}
