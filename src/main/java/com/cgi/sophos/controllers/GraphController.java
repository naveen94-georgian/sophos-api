package com.cgi.sophos.controllers;

import com.cgi.sophos.dto.AdUserDto;
import com.cgi.sophos.service.GraphService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public record GraphController(GraphService graphService) {

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/whoami")
  public AdUserDto myInfo(HttpServletRequest request) {
    return graphService.myInfo(request);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping()
  public AdUserDto getInfoById(@RequestParam("userid") String userId, HttpServletRequest request) {
    return graphService.getInfoById(userId, request);
  }
}
