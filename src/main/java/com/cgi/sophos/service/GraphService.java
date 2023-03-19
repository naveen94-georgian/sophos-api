package com.cgi.sophos.service;

import static com.cgi.sophos.mapper.Mappers.graphUserMapper;
import static java.util.Collections.emptyList;

import com.cgi.sophos.client.GraphClientBuilder;
import com.cgi.sophos.dto.GraphUserDto;
import com.microsoft.graph.models.User;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GraphService {
  private final GraphClientBuilder graphClientBuilder;

  public GraphUserDto myInfo(HttpServletRequest request) {
    var graphClient = graphClientBuilder.getClient(request);
    var user = graphClient.me().buildRequest().get();

    return graphUserMapper.toDTO(user);
  }

  public List<GraphUserDto> allInfo(HttpServletRequest request) {
    var graphClient = graphClientBuilder.getClient(request);
    var userIds =
        List.of(
            "e8d849b7-29b4-496d-a6e5-2421a4b7fc10",
            "890de6ad-0684-4b2a-a502-013bf98042d4",
            "2a6ed82a-a348-4019-9c75-e52a95437198");

    var collectionPage =
        graphClient
            .users()
            .buildRequest()
            .filter(getUserIdFilter(userIds))
            .select("id, displayName")
            .get();

    List<User> users = collectionPage != null ? collectionPage.getCurrentPage() : emptyList();
    return graphUserMapper.map(users);
  }

  String getUserIdFilter(List<String> userIds) {
    final String filterFormat = "id in (%s)";
    String filterParams =
        userIds.stream().map(m -> String.format("'%s'", m)).collect(Collectors.joining(","));
    return String.format(filterFormat, filterParams);
  }
}
