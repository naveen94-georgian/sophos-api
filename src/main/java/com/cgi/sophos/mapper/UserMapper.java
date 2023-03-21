package com.cgi.sophos.mapper;

import com.cgi.sophos.dto.AdUserDto;
import com.microsoft.graph.models.DirectoryObject;
import com.microsoft.graph.models.User;
import java.util.List;
import org.mapstruct.*;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(UserMapper.class);

  @Mapping(source = "manager", target = "manager", qualifiedByName = "mapManager")
  AdUserDto toDTO(User user);

  List<AdUserDto> toDTO(List<User> users);

  @Named("mapManager")
  static AdUserDto mapManager(DirectoryObject directoryObject) {
    var user = (User) directoryObject;
    return INSTANCE.toDTO(user);
  }
}
