package org.itsci.dao;

import org.itsci.model.Authority;

public interface AuthorityDao {
    void delete(int id);
    Authority findByAuthority(String authority);
}
