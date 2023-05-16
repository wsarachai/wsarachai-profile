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
@RequestMapping("/system/api")
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

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(MemberNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
