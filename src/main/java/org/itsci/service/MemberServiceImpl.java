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
    private MemberDao memberDao;

    @Override
    @Transactional
    public Member getMember(Long id) {
        return memberDao.getMember(id);
    }

    @Override
    @Transactional
    public Member updateMember(Member member) {
        member = memberDao.updateMember(member);
        return member;
    }

    @Override
    @Transactional
    public void saveMember(Member user) {
        memberDao.saveMember(user);
    }

    @Override
    @Transactional
    public List<Member> getMembers() {
        return memberDao.getMembers();
    }

    @Override
    @Transactional
    public void deleteMember(Long id) {
        memberDao.deleteMember(id);
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
        memberDao.saveMember(member);
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
        memberDao.saveMember(member);
    }
}
