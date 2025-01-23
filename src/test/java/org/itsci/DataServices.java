package org.itsci;

import org.hibernate.Session;
import org.itsci.model.AttenConfig;

public class DataServices {

    public static void populateAttenConfig(Session session) {
        session.beginTransaction();
        AttenConfig attenConfig = new AttenConfig();
        attenConfig.setOptionName("yearMinOption");
        attenConfig.setOptionValue("2010");
        session.save(attenConfig);
        attenConfig = new AttenConfig();
        attenConfig.setOptionName("yearMaxOption");
        attenConfig.setOptionValue("2012");
        session.save(attenConfig);
        session.getTransaction().commit();
    }
}
