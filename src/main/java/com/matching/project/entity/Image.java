package com.matching.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class Image {
    @Id @GeneratedValue
    private Long no;

    @Column(length = 255, nullable = false)
    private String logicalName;

    @Column(length = 255, nullable = false)
    private String physicalName;

    @Column(length = 255, nullable = false)
    private String url;
}
