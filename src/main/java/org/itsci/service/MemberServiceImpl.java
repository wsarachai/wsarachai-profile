package org.itsci.service;

import org.itsci.dao.AuthorityDao;
import org.itsci.dao.MemberDao;
import org.itsci.model.Authority;
import org.itsci.model.AuthorityType;
import org.itsci.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private MemberDao MemberDao;

    @Override
    @Transactional
    public Member getMember(Long id) {
        return MemberDao.getMember(id);
    }

    @Override
    @Transactional
    public void saveMember(Member user) {
        MemberDao.saveMember(user);
    }

    @Override
    @Transactional
    public List<Member> getMembers() {
        return MemberDao.getMembers();
    }

    @Override
    @Transactional
    public void deleteMember(Long id) {
        MemberDao.deleteMember(id);
    }

    @Override
    @Transactional
    public void updateMember(Member member, List<Authority> authorityToRemove, List<Authority> authorityToAdd) {
        for (Authority authority : authorityToRemove) {
            member.getLogin().getAuthorities().remove(authority);
        }
        for (Authority auth : authorityToAdd) {
            Authority authority = authorityDao.findByAuthority(auth.getAuthority());
            member.getLogin().getAuthorities().add(authority);
        }
        MemberDao.saveMember(member);
    }

    @Override
    @Transactional
    public void register(Member member) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encrypted = bCryptPasswordEncoder.encode(member.getLogin().getPassword().trim());
        member.getLogin().setPassword("{bcrypt}" + encrypted);
        Set<Authority> authorities = new HashSet<>();
        Authority authority = authorityDao.findByAuthority(AuthorityType.ROLE_MEMBER.toString());
        authorities.add(authority);
        member.getLogin().setAuthorities(authorities);
        member.getLogin().setEnabled(true);
        MemberDao.saveMember(member);
    }
}
