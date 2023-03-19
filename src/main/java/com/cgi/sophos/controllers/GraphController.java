package com.cgi.sophos.controllers;

import com.cgi.sophos.dto.GraphUserDto;
import com.cgi.sophos.service.GraphService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(name = "Graph")
@RestController
@RequiredArgsConstructor
public class GraphController {
  private final GraphService graphService;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(name = "myInfo")
  public GraphUserDto myInfo(HttpServletRequest request) {
    return graphService.myInfo(request);
  }
}
