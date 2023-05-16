package org.itsci.service;

import org.itsci.dao.AuthorityDao;
import org.itsci.dao.UserDao;
import org.itsci.model.Authority;
import org.itsci.model.AuthorityType;
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
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        user = userDao.updateUser(user);
        return user;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User user, List<Authority> authorityToRemove, List<Authority> authorityToAdd) {
        for (Authority authority : authorityToRemove) {
            user.getLogin().getAuthorities().remove(authority);
        }
        for (Authority auth : authorityToAdd) {
            Authority authority = authorityDao.findByAuthority(auth.getAuthority());
            user.getLogin().getAuthorities().add(authority);
        }
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void register(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encrypted = bCryptPasswordEncoder.encode(user.getLogin().getPassword().trim());
        user.getLogin().setPassword("{bcrypt}" + encrypted);
        Set<Authority> authorities = new HashSet<>();
        Authority authority = authorityDao.findByAuthority(AuthorityType.ROLE_MEMBER.toString());
        authorities.add(authority);
        user.getLogin().setAuthorities(authorities);
        user.getLogin().setEnabled(true);
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public Login getLoginById(Long id) {
        return userDao.getLoginById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }
}
