package com.cgi.sophos.service;

import static com.cgi.sophos.mapper.Mappers.USER_MAPPER;

import com.cgi.sophos.model.AdUser;
import com.cgi.sophos.model.Member;
import com.microsoft.graph.models.DirectoryObject;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import okhttp3.Request;
import org.springframework.stereotype.Service;

@Service
public record MemberService(GraphServiceClient<Request> graphClient) {

  public Member whoami() {
    var user = USER_MAPPER.toAdUser(graphClient.me().buildRequest().select(getGraphFields()).get());

    return Member.builder()
        .member(user)
        .manager(getManager(user.getId()))
        .directReports(getDirectReports(user.getId()))
        .build();
  }

  public Member getMemberById(@NotNull @NotEmpty String userId) {
    var user =
        USER_MAPPER.toAdUser(
            graphClient.users(userId).buildRequest().select(getGraphFields()).get());

    return Member.builder()
        .member(user)
        .manager(getManager(user.getId()))
        .directReports(getDirectReports(user.getId()))
        .build();
  }

  public AdUser getManager(@NotNull @NotEmpty String userId) {
    var directoryObject =
        graphClient.users(userId).manager().buildRequest().select(getGraphFields()).get();
    var manager = (User) directoryObject;
    return USER_MAPPER.toAdUser(manager);
  }

  public List<AdUser> getDirectReports(@NotNull @NotEmpty String userId) {
    var members =
        graphClient.users(userId).directReports().buildRequest().select(getGraphFields()).get();

    List<DirectoryObject> directoryObjects = new ArrayList<>();

    while (members != null) {
      directoryObjects.addAll(members.getCurrentPage());
      members = members.getNextPage() != null ? members.getNextPage().buildRequest().get() : null;
    }

    return directoryObjects.stream()
        .map(m -> (User) m)
        .map(USER_MAPPER::toAdUser)
        .collect(Collectors.toList());
  }

  String getGraphFields() {
    return Stream.of(AdUser.class.getDeclaredFields())
        .map(Field::getName)
        .collect(Collectors.joining(","));
  }
}
