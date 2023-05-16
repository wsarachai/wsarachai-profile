package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class MemberDaoImpl implements MemberDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Member> getMembers() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Member> criteria = builder.createQuery(Member.class);
        Root<Member> root = criteria.from(Member.class);
        criteria.select(root);

        Query<Member> query = session.createQuery(criteria);
        List<Member> users = query.getResultList();

        return users;
    }

    @Override
    public Member updateMember(Member member) {
        Session session = sessionFactory.getCurrentSession();
        member = (Member) session.merge(member);

        return member;
    }

    @Override
    public void saveMember(Member member) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(member);
    }

    @Override
    public Member getMember(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Member.class, id);
    }

    @Override
    public void deleteMember(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Member member = session.get(Member.class, id);
        session.delete(member);
        session.flush() ;
    }
}
