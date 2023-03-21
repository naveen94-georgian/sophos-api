package com.cgi.sophos.dto;

import lombok.Data;

@Data
public class AdUserDto {
  private String id;
  private String displayName;
  private String department;
  private String employeeId;
  private String jobTitle;
  private String mail;
  private String officeLocation;
  private String streetAddress;
  private String country;
  private String usageLocation;
  private String preferredLanguage;
  AdUserDto manager;
}
