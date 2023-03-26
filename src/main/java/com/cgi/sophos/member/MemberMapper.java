package com.cgi.sophos.member;

import com.microsoft.graph.models.User;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MemberMapper {
  Member toMember(User user);

  List<Member> toMember(List<User> users);
}
