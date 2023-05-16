package org.itsci.dao;

import org.itsci.model.Member;

import java.util.List;

public interface MemberDao {

    List<Member> getMembers();

    Member updateMember(Member member);

    void saveMember(Member member);

    Member getMember(Long id);

    void deleteMember(Long id);
}
