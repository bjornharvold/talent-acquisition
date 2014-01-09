package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 12:43:07 PM
 * Responsibility:
 */
public class ProjectEndorsementDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1033L;
    private String project;
    private EndorsementDto endorsement;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public EndorsementDto getEndorsement() {
        return endorsement;
    }

    public void setEndorsement(EndorsementDto endorsement) {
        this.endorsement = endorsement;
    }
}
