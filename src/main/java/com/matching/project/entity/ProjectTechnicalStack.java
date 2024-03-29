package com.matching.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTechnicalStack extends BaseTimeEntity{
    @Id
    @GeneratedValue
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technical_stack_no")
    private TechnicalStack technicalStack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_no")
    private Project project;

    public void setProject(Project project) {
        if(this.project != null) {
            this.project.getProjectTechnicalStackList().remove(this);
        }
        this.project = project;
        project.getProjectTechnicalStackList().add(this);
    }
}
