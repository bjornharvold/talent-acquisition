package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 11:14:16 AM
 * Responsibility:
 */
public class BiographySkillEndorsementDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1009L;
    private String biographySkill;
    private EndorsementDto endorsement;

    public String getBiographySkill() {
        return biographySkill;
    }

    public void setBiographySkill(String biographySkill) {
        this.biographySkill = biographySkill;
    }

    public EndorsementDto getEndorsement() {
        return endorsement;
    }

    public void setEndorsement(EndorsementDto endorsement) {
        this.endorsement = endorsement;
    }
}
