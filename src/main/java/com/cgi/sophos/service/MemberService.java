package com.cgi.sophos.service;

import static com.cgi.sophos.mapper.Mappers.USER_MAPPER;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.cgi.sophos.model.Member;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.UserCollectionPage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import okhttp3.Request;
import org.springframework.stereotype.Service;

@Service
public record MemberService(GraphServiceClient<Request> graphClient) {

  public Member getMember(String userId) {
    var user =
        isNotEmpty(userId)
            ? graphClient.users(userId).buildRequest().select(getGraphFields()).get()
            : graphClient.me().buildRequest().select(getGraphFields()).get();
    return USER_MAPPER.toMember(user);
  }

  public Member getManager(String userId) {
    var directoryObject =
        isNotEmpty(userId)
            ? graphClient.users(userId).manager().buildRequest().select(getGraphFields()).get()
            : graphClient.me().manager().buildRequest().select(getGraphFields()).get();
    return USER_MAPPER.toMember((User) directoryObject);
  }

  public List<Member> getDirectReports(String userId) {
    var userCollectionPage =
        isNotEmpty(userId)
            ? graphClient
                .users(userId)
                .directReportsAsUser()
                .buildRequest()
                .select(getGraphFields())
                .get()
            : graphClient.me().directReportsAsUser().buildRequest().select(getGraphFields()).get();

    return convertCollectionPageToMemberList(userCollectionPage);
  }

  public List<Member> lookUp(String email, String givenName, String surname) {
    String filter = null;
    if (isNotEmpty(email)) {
      filter = String.format("mail eq '%s'", email);
    } else if (isNotEmpty(givenName) && isNotEmpty(surname)) {
      filter = String.format("givenName eq '%s' and surname eq '%s'", givenName, surname);
    } else if (isNotEmpty(givenName)) {
      filter = String.format("givenName eq '%s'", givenName);
    } else if (isNotEmpty(surname)) {
      filter = String.format("surname eq '%s'", surname);
    }

    if (isNotEmpty(filter)) {
      var userPage =
          graphClient.users().buildRequest().filter(filter).select(getGraphFields()).get();
      return convertCollectionPageToMemberList(userPage);
    }
    return emptyList();
  }

  List<Member> convertCollectionPageToMemberList(UserCollectionPage page) {
    List<User> users = new ArrayList<>();
    while (page != null) {
      users.addAll(page.getCurrentPage());
      page = page.getNextPage() != null ? page.getNextPage().buildRequest().get() : null;
    }
    return USER_MAPPER.toMember(users);
  }

  String getGraphFields() {
    return Stream.of(Member.class.getDeclaredFields())
        .map(Field::getName)
        .collect(Collectors.joining(","));
  }
}
