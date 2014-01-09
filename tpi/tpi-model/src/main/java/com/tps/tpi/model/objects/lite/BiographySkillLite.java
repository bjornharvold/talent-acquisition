package com.tps.tpi.model.objects.lite;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Nov 10, 2009
 * Time: 1:38:43 AM
 * Responsibility:
 */
public class BiographySkillLite extends AbstractLite implements Serializable {
    private final static long serialVersionUID = 10003L;
    private String skill;
    private String proficiency;
    private Integer endorsements;
    private Integer averageStars;

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public Integer getEndorsements() {
        return endorsements;
    }

    public void setEndorsements(Integer endorsements) {
        this.endorsements = endorsements;
    }

    public Integer getAverageStars() {
        return averageStars;
    }

    public void setAverageStars(Integer averageStars) {
        this.averageStars = averageStars;
    }
}
