package com.cgi.sophos.controllers;

import com.cgi.sophos.model.Member;
import com.cgi.sophos.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public record GraphController(MemberService memberService) {

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/whoami")
  public Member myInfo() {
    return memberService.whoami();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping()
  public Member getInfoById(@RequestParam("userid") String userId) {
    return memberService.getMemberById(userId);
  }
}
