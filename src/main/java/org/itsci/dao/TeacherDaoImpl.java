package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TeacherDaoImpl implements TeacherDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public List<Teacher> findAll() {
    try {
      Session session = sessionFactory.getCurrentSession();

      // Use a very simple native SQL query to avoid any Hibernate complexity
      String sql = "SELECT * FROM teacher";
      Query<Teacher> query = session.createNativeQuery(sql, Teacher.class);

      return query.getResultList();
    } catch (Exception e) {
      // If native query fails, try HQL
      try {
        Session session = sessionFactory.getCurrentSession();
        Query<Teacher> query = session.createQuery("FROM Teacher", Teacher.class);
        return query.getResultList();
      } catch (Exception e2) {
        // If all else fails, return empty list
        System.err.println("Error in TeacherDao.findAll(): " + e2.getMessage());
        e2.printStackTrace();
        return new ArrayList<>();
      }
    }
  }

  @Override
  public Teacher getById(Long id) {
    Session session = sessionFactory.getCurrentSession();
    return session.get(Teacher.class, id);
  }

  @Override
  public void save(Teacher teacher) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(teacher);
  }

  @Override
  public void update(Teacher teacher) {
    Session session = sessionFactory.getCurrentSession();
    session.update(teacher);
  }

  @Override
  public void delete(Long id) {
    Session session = sessionFactory.getCurrentSession();
    Teacher teacher = session.get(Teacher.class, id);
    if (teacher != null) {
      session.delete(teacher);
    }
  }
}
