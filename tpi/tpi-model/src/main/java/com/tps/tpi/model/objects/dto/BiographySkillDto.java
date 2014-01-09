package com.tps.tpi.model.objects.dto;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 3:46:53 PM
 * Responsibility:
 */
public class BiographySkillDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1007L;
    private String biography;
    private List<BiographySkillEndorsementDto> biographySkillEndorsements;
    private SkillDto skill;
    private String proficiency;
    private Integer years;

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<BiographySkillEndorsementDto> getBiographySkillEndorsements() {
        return biographySkillEndorsements;
    }

    public void setBiographySkillEndorsements(List<BiographySkillEndorsementDto> biographySkillEndorsements) {
        this.biographySkillEndorsements = biographySkillEndorsements;
    }

    public SkillDto getSkill() {
        return skill;
    }

    public void setSkill(SkillDto skill) {
        this.skill = skill;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public void addBiographySkillEndorsement(BiographySkillEndorsementDto dto) {
        if (biographySkillEndorsements == null) {
            biographySkillEndorsements = new ArrayList<BiographySkillEndorsementDto>();
        }

        boolean isNew = true;

        for (BiographySkillEndorsementDto aDto : biographySkillEndorsements) {
            if (StringUtils.equals(dto.getId(), aDto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            biographySkillEndorsements.add(dto);
        }
    }
}
