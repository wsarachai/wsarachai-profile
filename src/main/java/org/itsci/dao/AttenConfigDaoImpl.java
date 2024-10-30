package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.AttenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class AttenConfigDaoImpl implements AttenConfigDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @Cacheable(value = "config", key = "#optionName")
    public String getOptionValueByName(String optionName) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<AttenConfig> criteria = builder.createQuery(AttenConfig.class);
        Root<AttenConfig> root = criteria.from(AttenConfig.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("optionName"), optionName));

        Query<AttenConfig> query = session.createQuery(criteria);
        AttenConfig result = query.uniqueResult();
        return result.getOptionValue();
    }
}
