package com.cgi.sophos.mapper;

import com.cgi.sophos.model.Member;
import com.microsoft.graph.models.User;
import java.util.List;
import org.mapstruct.*;

@Mapper
public interface UserMapper {
  Member toMember(User user);

  List<Member> toMember(List<User> users);
}
