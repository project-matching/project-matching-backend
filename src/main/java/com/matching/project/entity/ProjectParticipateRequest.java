package com.matching.project.entity;

import javax.persistence.*;

@Entity
public class ProjectParticipateRequest {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User userNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_position_no")
    private ProjectPosition projectPosition;
}