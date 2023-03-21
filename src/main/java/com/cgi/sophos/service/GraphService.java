package com.cgi.sophos.service;

import static com.cgi.sophos.mapper.Mappers.graphUserMapper;

import com.cgi.sophos.client.GraphClientBuilder;
import com.cgi.sophos.model.AdUser;
import com.cgi.sophos.model.Member;
import com.microsoft.graph.models.DirectoryObject;
import com.microsoft.graph.models.User;
import jakarta.servlet.http.HttpServletRequest;
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
public record GraphService(GraphClientBuilder graphClientBuilder) {

  public Member whoami(HttpServletRequest request) {
    var graphClient = graphClientBuilder.getClient(request);
    var user =
        graphUserMapper.toDTO(graphClient.me().buildRequest().select(getSelectedFields()).get());

    return new Member(
        user, getManager(user.getId(), graphClient), getDirectReports(user.getId(), graphClient));
  }

  public Member getMemberById(@NotNull @NotEmpty String userId, HttpServletRequest request) {
    var graphClient = graphClientBuilder.getClient(request);

    var user =
        graphUserMapper.toDTO(
            graphClient.users(userId).buildRequest().select(getSelectedFields()).get());

    return new Member(
        user, getManager(user.getId(), graphClient), getDirectReports(user.getId(), graphClient));
  }

  public AdUser getManager(
      @NotNull @NotEmpty String userId, com.microsoft.graph.requests.GraphServiceClient<Request> graphServiceClient) {
    var directoryObject =
        graphServiceClient.users(userId).manager().buildRequest().select(getSelectedFields()).get();
    var manager = (User) directoryObject;
    return graphUserMapper.toDTO(manager);
  }

  public List<AdUser> getDirectReports(
      @NotNull @NotEmpty String userId, com.microsoft.graph.requests.GraphServiceClient<Request> graphServiceClient) {
    var members =
        graphServiceClient.users(userId).directReports().buildRequest().select(getSelectedFields()).get();

    List<DirectoryObject> directoryObjects = new ArrayList<>();

    while (members != null) {
      directoryObjects.addAll(members.getCurrentPage());
      if (members.getNextPage() == null) break;
      members = members.getNextPage().buildRequest().get();
    }

    return directoryObjects.stream()
        .map(m -> (User) m)
        .map(graphUserMapper::toDTO)
        .collect(Collectors.toList());
  }

  String getSelectedFields() {
    return Stream.of(AdUser.class.getDeclaredFields())
        .map(Field::getName)
        .collect(Collectors.joining(","));
  }

  //  public AdUser getAllDirectory(HttpServletRequest request) {
  //    var graphClient = graphClientBuilder.getClient(request);
  //    LinkedList<Option> requestOptions = new LinkedList<Option>();
  //    requestOptions.add(new HeaderOption("ConsistencyLevel", "eventual"));
  //
  //    var selectNames =
  //        Stream.of(AdUser.class.getDeclaredFields())
  //            .map(Field::getName)
  //            .collect(Collectors.joining(","));
  //
  //    var user =
  //        graphClient
  //            .me()
  //            .buildRequest(requestOptions)
  //            .expand(MessageFormat.format("manager($levels=max;$select={0})", selectNames))
  //            .select(selectNames)
  //            .get();
  //    return graphUserMapper.toDTO(user);
  //  }
}
