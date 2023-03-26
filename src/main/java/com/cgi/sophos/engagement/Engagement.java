package com.cgi.sophos.engagement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Getter
@Setter
@Table(name = "ENGAGEMENT")
public class Engagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LINE_OF_BUSINESS")
    private String lineOfBusiness;

    @Column(name = "ENGAGEMENT_NUMBER")
    private String engagementNumber;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CLIENT_NAME")
    private String clientName;

    @ElementCollection
    @Column(name = "COLLABORATING_BUSINESS_UNITS")
    private List<String> collaboratingBUs;

    @Column(name = "ENSEMBLE_URL")
    private String ensembleUrl;

    @Column(name = "SDM")
    private String deliveryManager;

    @ElementCollection
    @Column(name = "DELEGATES")
    List<String> delegates;
}
