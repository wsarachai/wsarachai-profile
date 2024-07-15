package org.itsci.controller.rest;

import org.itsci.controller.rest.exception.MemberNotFoundException;
import org.itsci.model.Login;
import org.itsci.model.Member;
import org.itsci.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test")
public class TestApi {

    @Autowired
    MemberService memberService;

    @GetMapping("/")
    public List<Member> getMembers() {
        return memberService.getMembers();
    }

    @GetMapping(path="/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Login getMembers(@PathVariable long memberId) {
        Member member = memberService.getMember(memberId);
        if (member == null) {
            throw new MemberNotFoundException("Member is not found - " + memberId);
        }
        return member.getLogin();
    }

    @PostMapping("/")
    public Member addMember(@RequestBody Member member) {
        return member;
    }

    @PostMapping("/resource")
    public ResponseEntity<String> createResource(@RequestBody String requestBody) {
        return ResponseEntity.ok("Resource created successfully");
    }
}
