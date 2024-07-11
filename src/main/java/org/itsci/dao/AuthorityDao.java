package org.itsci.dao;

import org.itsci.model.Authority;
import org.itsci.model.EAuthorityType;

import java.util.Set;

public interface AuthorityDao {
    void delete(int id);
    Authority findByRole(EAuthorityType role);

    Set<Authority> findAll();

    void save(Authority authority);
}
