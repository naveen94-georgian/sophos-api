package com.cgi.sophos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GraphUserDto {
  private String id;
  private String displayName;
}
