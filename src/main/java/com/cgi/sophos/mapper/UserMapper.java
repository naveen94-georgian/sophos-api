package com.cgi.sophos.mapper;

import com.cgi.sophos.model.AdUser;
import com.microsoft.graph.models.User;
import java.util.List;
import org.mapstruct.*;

@Mapper
public interface UserMapper {
  AdUser toAdUser(User user);

  List<AdUser> toAdUser(List<User> users);
}
