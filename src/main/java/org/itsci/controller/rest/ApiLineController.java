package org.itsci.controller.rest;


import org.itsci.model.Member;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/line")
public class ApiLineController {

    @PostMapping("/")
    public Member addMember(@RequestBody Member member) {
        return member;
    }
}
