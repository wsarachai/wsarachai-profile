package org.itsci.service;

import org.itsci.dao.AuthorityDao;
import org.itsci.dao.UserDao;
import org.itsci.model.Authority;
import org.itsci.model.EAuthorityType;
import org.itsci.model.Login;
import org.itsci.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl<T extends User> implements UserService<T>, UserDetailsService {

    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private UserDao<T> userDao;

    @Override
    @Transactional
    public T getUser(Long id, Class<?> c) {
        T user = userDao.getById(id, c);
        return user;
    }

    @Override
    @Transactional
    public T findByUsernameGeneric(String username) {
        return userDao.findByUsernameGeneric(username);
    }

    @Override
    @Transactional
    public T updateUser(T user) {
        user = userDao.update(user);
        return user;
    }

    @Override
    @Transactional
    public void saveUser(T user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public List<T> getUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public void updateUser(T user, List<Authority> authorityToRemove, List<Authority> authorityToAdd) {
        for (Authority authority : authorityToRemove) {
            ((User)user).getLogin().getAuthorities().remove(authority);
        }
        for (Authority auth : authorityToAdd) {
            Authority authority = authorityDao.findByRole(auth.getRoleName());
            ((User)user).getLogin().getAuthorities().add(authority);
        }
        userDao.update(user);
    }

    @Override
    @Transactional
    public void register(T user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encrypted = bCryptPasswordEncoder.encode(((User)user).getLogin().getPassword().trim());
        ((User)user).getLogin().setPassword("{bcrypt}" + encrypted);
        Set<Authority> authorities = new HashSet<>();
        Authority authority = authorityDao.findByRole(EAuthorityType.ROLE_MEMBER);
        authorities.add(authority);
        ((User)user).getLogin().setAuthorities(authorities);
        ((User)user).getLogin().setEnabled(true);
        userDao.save(user);
    }

    @Override
    @Transactional
    public Login getLoginById(Long id) {
        return userDao.getLoginById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username);
    }
}
