package org.itsci.dao;

import org.itsci.model.Image;

public interface ImageDao {
    void save(Image image);
    void saveOrUpdate(Image image);
    Image getByID(long image_id);
}
