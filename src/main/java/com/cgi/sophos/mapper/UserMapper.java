package com.cgi.sophos.mapper;

import com.cgi.sophos.model.AdUser;
import com.microsoft.graph.models.User;
import java.util.List;
import org.mapstruct.*;

@Mapper
public interface UserMapper {
  //  UserMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(UserMapper.class);

  //  @Mapping(source = "manager", target = "manager", qualifiedByName = "mapManager")
  AdUser toDTO(User user);

  List<AdUser> toDTO(List<User> users);

  //  @Named("mapManager")
  //  static AdUser mapManager(DirectoryObject directoryObject) {
  //    var user = (User) directoryObject;
  //    return INSTANCE.toDTO(user);
  //  }
}
