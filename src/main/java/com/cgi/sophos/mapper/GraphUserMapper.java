package com.cgi.sophos.mapper;

import com.cgi.sophos.dto.GraphUserDto;
import com.microsoft.graph.models.User;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public interface GraphUserMapper {
  GraphUserDto toDTO(User user);

  List<GraphUserDto> map(List<User> users);
}
