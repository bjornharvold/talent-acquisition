package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 12:40:46 PM
 * Responsibility:
 */
public class PositionEndorsementDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1029L;
    private String position;
    private EndorsementDto endorsement;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public EndorsementDto getEndorsement() {
        return endorsement;
    }

    public void setEndorsement(EndorsementDto endorsement) {
        this.endorsement = endorsement;
    }
}
