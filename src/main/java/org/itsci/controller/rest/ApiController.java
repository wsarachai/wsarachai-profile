package org.itsci.controller.rest;

import org.itsci.model.Member;
import org.itsci.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//
//@RestController
//@RequestMapping("/system/api")
public class ApiController {
//
//    @Autowired
//    MemberService memberService;
//
//    @GetMapping("/members")
//    public List<Member> getMembers() {
//        return memberService.getMembers();
//    }
//
//    @GetMapping("/members/{memberId}")
//    public Member getMembers(@PathVariable long memberId) {
//        return memberService.getMember(memberId);
//    }

//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleException(MemberNotFoundException ex) {
//        ErrorResponse error = new ErrorResponse();
//        error.setStatus(HttpStatus.NOT_FOUND.value());
//        error.setMessage(ex.getMessage());
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }
}
