package org.itsci.controller.rest;

import org.itsci.controller.rest.error.ErrorResponse;
import org.itsci.controller.rest.exception.MemberNotFoundException;
import org.itsci.model.Member;
import org.itsci.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/api/v1")
public class ApiController {

    @Autowired
    MemberService memberService;

    @GetMapping("/members")
    public List<Member> getMembers() {
        return memberService.getMembers();
    }

    @GetMapping("/members/{memberId}")
    public Member getMembers(@PathVariable long memberId) {
        Member member = memberService.getMember(memberId);
        if (member == null) {
            throw new MemberNotFoundException("Member is not found - " + memberId);
        }
        return member;
    }

    @GetMapping("/members/add")
    public void addMember(@RequestBody Member member) {
        memberService.saveMember(member);
    }
}
