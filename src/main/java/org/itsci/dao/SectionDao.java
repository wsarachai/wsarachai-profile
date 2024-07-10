package org.itsci.dao;

import org.itsci.model.Section;

public interface SectionDao {
    Section getCourseSection(Long id);

    void save(Section section);
}
