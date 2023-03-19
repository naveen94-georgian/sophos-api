package com.cgi.sophos.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Mappers {
  public static final GraphUserMapper graphUserMapper =
      org.mapstruct.factory.Mappers.getMapper(GraphUserMapper.class);
}
