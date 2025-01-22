package org.itsci;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.itsci.model.*;

import java.util.Properties;

public abstract class HibernateUtil {

    public static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        Properties configuration = new Properties();
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:testdb");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");

        Configuration cfg = new Configuration().setProperties(configuration)
                .addPackage("bean")
                .addAnnotatedClass(AttenConfig.class)
                .addAnnotatedClass(Attendance.class)
                .addAnnotatedClass(Authority.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Curriculum.class)
                .addAnnotatedClass(Enrollment.class)
                .addAnnotatedClass(Image.class)
                .addAnnotatedClass(Login.class)
                .addAnnotatedClass(Member.class)
                .addAnnotatedClass(Room.class)
                .addAnnotatedClass(Section.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Subject.class)
                .addAnnotatedClass(Teacher.class)
                .addAnnotatedClass(TeacherCourse.class)
                .addAnnotatedClass(User.class);
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties());
        sessionFactory = cfg.buildSessionFactory(ssrb.build());
        return sessionFactory;
    }
}
