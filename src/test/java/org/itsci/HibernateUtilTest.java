package org.itsci;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.itsci.model.AttenConfig;
import org.junit.jupiter.api.*;

import java.util.List;


public class HibernateUtilTest {
    private static SessionFactory sessionFactory;
    private Session session = null;

    @BeforeAll
    public static void setup() {
        sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println("SessionFactory created");
    }

    @AfterAll
    public static void tearDown() {
        if (sessionFactory != null) sessionFactory.close();
        System.out.println("SessionFactory destroyed");
    }
    @Test
    public void testCreate() {
        DataServices.populateAttenConfig(session);
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testGet() {
    }

    @Test
    public void testList() {
        DataServices.populateAttenConfig(session);

        session.beginTransaction();
        List<AttenConfig> attendanceList = session.createQuery("from AttenConfig", AttenConfig.class).list();
        assert !attendanceList.isEmpty();
        for (AttenConfig ac : attendanceList) {
            System.out.println(ac.getOptionName());
            System.out.println(ac.getOptionValue());
        }
        session.getTransaction().commit();
    }

    @Test
    public void testDelete() {
    }

    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();
        System.out.println("Session created");
    }

    @AfterEach
    public void closeSession() {
        if (session != null) session.close();
        System.out.println("Session closed\n");
    }
}
