package com.cgi.sophos.controllers;

import com.cgi.sophos.model.Member;
import com.cgi.sophos.service.GraphService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public record GraphController(GraphService graphService) {

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/whoami")
  public Member myInfo(HttpServletRequest request) {
    return graphService.whoami(request);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping()
  public Member getInfoById(@RequestParam("userid") String userId, HttpServletRequest request) {
    return graphService.getMemberById(userId, request);
  }
}
