package org.itsci.controller.rest;

import org.itsci.controller.rest.exception.MemberNotFoundException;
import org.itsci.model.Member;
import org.itsci.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class ApiController {

    @Autowired
    MemberService memberService;

    @GetMapping("/")
    public List<Member> getMembers() {
        return memberService.getMembers();
    }

    @GetMapping("/{memberId}")
    public Member getMembers(@PathVariable long memberId) {
        Member member = memberService.getMember(memberId);
        if (member == null) {
            throw new MemberNotFoundException("Member is not found - " + memberId);
        }
        return member;
    }

    @PostMapping("/")
    public Member addMember(@RequestBody Member member) {
        return member;
    }
}
