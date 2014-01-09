package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 12:39:32 PM
 * Responsibility:
 */
public class CoverageEndorsementDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1015L;
    private String coverage;
    private EndorsementDto endorsement;

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public EndorsementDto getEndorsement() {
        return endorsement;
    }

    public void setEndorsement(EndorsementDto endorsement) {
        this.endorsement = endorsement;
    }
}
