package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.itsci.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDaoImpl implements ImageDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Image image) {
        Session session = sessionFactory.getCurrentSession();
        session.save(image);
    }

    @Override
    public void saveOrUpdate(Image image) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(image);
    }

    @Override
    public Image getByID(long id) {
        Session session = sessionFactory.getCurrentSession();
        Image image = session.get(Image.class, id);
        return image;
    }
}
