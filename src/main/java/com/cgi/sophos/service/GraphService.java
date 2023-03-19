package com.cgi.sophos.service;

import com.cgi.sophos.client.GraphClientBuilder;
import com.microsoft.graph.models.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GraphService {
  private final GraphClientBuilder graphClientBuilder;

  public User myInfo(HttpServletRequest request) {
    var graphClient = graphClientBuilder.getClient(request);
    return graphClient.me().buildRequest().get();
  }
}
