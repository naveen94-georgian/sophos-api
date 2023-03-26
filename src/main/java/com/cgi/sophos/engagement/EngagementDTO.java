package com.cgi.sophos.engagement;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.Data;

import java.util.List;

@Data
public class EngagementDTO {
    private Long id;
    private String name;
    private String lineOfBusiness;
    private String engagementNumber;
    private String description;
    private String clientName;
    private List<String> collaboratingBUs;
    private String ensembleUrl;
    private String deliveryManager;
    List<String> delegates;
}
