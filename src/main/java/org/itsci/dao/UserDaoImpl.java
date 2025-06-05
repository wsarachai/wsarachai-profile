package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Login;
import org.itsci.model.Student;
import org.itsci.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository
public class UserDaoImpl<T extends User> implements UserDao<T> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Change this to use the generic type parameter
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];

        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> root = criteria.from(entityClass);
        criteria.select(root);

        Query<T> query = session.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public User findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("login").get("username"), username));

        Query<User> query = session.createQuery(criteria);
        User user = query.getSingleResult();
        return user;
    }

    @Override
    public Student findByStudentId(String studentId) {
        Student student = null;
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> criteria = builder.createQuery(Student.class);
        Root<Student> root = criteria.from(Student.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("studentId"), studentId));

        Query<Student> query = session.createQuery(criteria);
        try {
            student = query.getSingleResult();
        } catch (Exception ex) {
            if (student == null) {
                System.out.println(ex.getMessage());
            }
        }
        return student;
    }

    @Override
    public void saveOrUpdate(T user) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }

    @Override
    public T update(T user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
        return user;
    }

    @Override
    public void save(T user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Override
    public T getById(Long id, Class<?> c) {
        Session session = sessionFactory.getCurrentSession();
        T user = (T) session.get(c, id);
        return user;
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.load(User.class, id);
        session.delete(user);
        session.flush();
    }

    @Override
    public Login getLoginById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.load(Login.class, id);
    }

    @Override
    public T findByUsernameGeneric(String username) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<?> root = criteria.from(User.class);
        criteria.select((Selection<? extends User>) root);
        criteria.where(builder.equal(root.get("login").get("username"), username));

        Query<User> query = session.createQuery(criteria);
        User result = query.getSingleResult();
        return (T) result;
    }
}
