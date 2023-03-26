package com.cgi.sophos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
  private String id;
  private String displayName;
  private String givenName;
  private String surname;
  private String department;
  private String employeeId;
  private String jobTitle;
  private String mail;
  private String officeLocation;
  private String streetAddress;
  private String country;
  private String mobilePhone;
  private String usageLocation;
  private String preferredLanguage;
}
