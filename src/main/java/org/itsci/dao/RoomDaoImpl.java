package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class RoomDaoImpl implements RoomDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Room> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Room> criteria = builder.createQuery(Room.class);
        Root<Room> root = criteria.from(Room.class);
        criteria.select(root);
        Query<Room> query = session.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public void save(Room room) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(room);
    }

    @Override
    public Room getRoomById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Room room = session.get(Room.class, id);
        return room;
    }
}
