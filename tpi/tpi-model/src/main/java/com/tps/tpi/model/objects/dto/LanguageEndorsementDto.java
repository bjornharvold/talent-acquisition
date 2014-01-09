package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 4:01:42 PM
 * Responsibility:
 */
public class LanguageEndorsementDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1024L;
    private String language;
    private EndorsementDto endorsement;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public EndorsementDto getEndorsement() {
        return endorsement;
    }

    public void setEndorsement(EndorsementDto endorsement) {
        this.endorsement = endorsement;
    }
}
