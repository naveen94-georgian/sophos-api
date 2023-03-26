package com.cgi.sophos.member;

import com.microsoft.graph.models.User;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MemberMapper {
  MemberDTO toMember(User user);

  List<MemberDTO> toMember(List<User> users);
}
