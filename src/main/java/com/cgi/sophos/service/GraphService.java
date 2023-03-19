package com.cgi.sophos.service;

import com.cgi.sophos.client.GraphClientBuilder;
import com.cgi.sophos.dto.GraphUserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GraphService {
  private final GraphClientBuilder graphClientBuilder;

  public GraphUserDto myInfo(HttpServletRequest request) {
    var graphClient = graphClientBuilder.getClient(request);
    var user = graphClient.me().buildRequest().get();

    return user != null
        ? GraphUserDto.builder().id(user.id).displayName(user.displayName).build()
        : null;
  }
}
