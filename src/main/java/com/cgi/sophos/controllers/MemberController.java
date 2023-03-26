package com.cgi.sophos.controllers;

import com.cgi.sophos.model.Member;
import com.cgi.sophos.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public Member getMember(@RequestParam(value = "userid", required = false) String userId) {
    return memberService.getMember(userId);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/manager")
  public Member getManager(@RequestParam(value = "userid", required = false) String userId) {
    return memberService.getManager(userId);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/direct-reports")
  public List<Member> getDirectReports(
      @RequestParam(value = "userid", required = false) String userId) {
    return memberService.getDirectReports(userId);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/lookup")
  public List<Member> lookUp(
      @RequestParam(value = "email", required = false) @Valid @Email String email,
      @RequestParam(value = "givenName", required = false) String givenName,
      @RequestParam(value = "surname", required = false) String surname) {
    return memberService.lookUp(email, givenName, surname);
  }
}
