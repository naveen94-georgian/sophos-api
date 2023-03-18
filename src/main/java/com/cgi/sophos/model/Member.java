package com.cgi.sophos.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
  private AdUser member;
  private AdUser manager;
  private List<AdUser> directReports;
}
