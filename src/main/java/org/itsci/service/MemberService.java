package org.itsci.service;

import org.itsci.model.Authority;
import org.itsci.model.Member;

import java.util.List;

public interface MemberService {

    Member getMember(Long id);

    Member updateMember(Member user);

    void saveMember(Member user);

    List<Member> getMembers();

    void deleteMember(Long id);

    void updateMember(Member member, List<Authority> authorityToRemove, List<Authority> authorityToAdd);

    void register(Member member);
}
