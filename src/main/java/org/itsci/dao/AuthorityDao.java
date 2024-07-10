package org.itsci.dao;

import org.itsci.model.Authority;

import java.util.Set;

public interface AuthorityDao {
    void delete(int id);
    Authority findByAuthority(String authority);

    Set<Authority> findAll();

    void save(Authority authority);
}
