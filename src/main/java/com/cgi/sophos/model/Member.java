package com.cgi.sophos.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Member {
  private AdUser member;
  private AdUser manager;
  private List<AdUser> directReports;
}
