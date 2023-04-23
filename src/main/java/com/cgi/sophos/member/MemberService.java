package com.cgi.sophos.member;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.cgi.sophos.common.config.LocalGraphClient;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.UserCollectionPage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final LocalGraphClient graphClient;
  private final MemberMapper memberMapper;

  public MemberDTO getMember(String userId) {
    var user =
        isNotEmpty(userId)
            ? graphClient.getClient().users(userId).buildRequest().select(getGraphFields()).get()
            : graphClient.getClient().me().buildRequest().select(getGraphFields()).get();
    return memberMapper.toMember(user);
  }

  public MemberDTO getManager(String userId) {
    var directoryObject =
        isNotEmpty(userId)
            ? graphClient.getClient().users(userId).manager().buildRequest().select(getGraphFields()).get()
            : graphClient.getClient().me().manager().buildRequest().select(getGraphFields()).get();
    return memberMapper.toMember((User) directoryObject);
  }

  public List<MemberDTO> getDirectReports(String userId) {
    var userCollectionPage =
        isNotEmpty(userId)
            ? graphClient.getClient()
                .users(userId)
                .directReportsAsUser()
                .buildRequest()
                .select(getGraphFields())
                .get()
            : graphClient.getClient().me().directReportsAsUser().buildRequest().select(getGraphFields()).get();

    return convertCollectionPageToMemberList(userCollectionPage);
  }

  public List<MemberDTO> lookUp(String email, String givenName, String surname) {
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
              graphClient.getClient().users().buildRequest().filter(filter).select(getGraphFields()).get();
      return convertCollectionPageToMemberList(userPage);
    }
    return emptyList();
  }

  List<MemberDTO> convertCollectionPageToMemberList(UserCollectionPage page) {
    List<User> users = new ArrayList<>();
    while (page != null) {
      users.addAll(page.getCurrentPage());
      page = page.getNextPage() != null ? page.getNextPage().buildRequest().get() : null;
    }
    return memberMapper.toMember(users);
  }

  String getGraphFields() {
    return Stream.of(MemberDTO.class.getDeclaredFields())
        .map(Field::getName)
        .collect(Collectors.joining(","));
  }
}
