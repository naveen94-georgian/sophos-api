package com.cgi.sophos.controllers;

import com.cgi.sophos.dto.GraphUserDto;
import com.cgi.sophos.service.GraphService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class GraphController {
  private final GraphService graphService;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/whoami")
  public GraphUserDto myInfo(HttpServletRequest request) {
    return graphService.myInfo(request);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/all")
  public List<GraphUserDto> allInfo(HttpServletRequest request) {
    return graphService.allInfo(request);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping()
  public GraphUserDto getInfoById(
      @RequestParam("userid") String userId, HttpServletRequest request) {
    return graphService.getInfoById(userId, request);
  }
}
