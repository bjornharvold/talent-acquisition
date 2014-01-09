package com.tps.tpi.model.objects.dto;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 3:43:53 PM
 * Responsibility:
 */
public class BiographyDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1006L;
    private String person;
    private String summary;
    private List<BiographySkilledRoleDto> biographySkilledRoles;
    private List<BiographyDepartmentDto> biographyDepartments;
    private List<BiographyCityDto> biographyCities;
    private List<BiographySkillDto> biographySkills;
    private List<PatentDto> patents;
    private List<AwardDto> awards;
    private List<PublicationDto> publications;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<BiographySkilledRoleDto> getBiographySkilledRoles() {
        return biographySkilledRoles;
    }

    public void setBiographySkilledRoles(List<BiographySkilledRoleDto> biographySkilledRoles) {
        this.biographySkilledRoles = biographySkilledRoles;
    }

    public List<BiographyDepartmentDto> getBiographyDepartments() {
        return biographyDepartments;
    }

    public void setBiographyDepartments(List<BiographyDepartmentDto> biographyDepartments) {
        this.biographyDepartments = biographyDepartments;
    }

    public List<BiographyCityDto> getBiographyCities() {
        return biographyCities;
    }

    public void setBiographyCities(List<BiographyCityDto> biographyCities) {
        this.biographyCities = biographyCities;
    }

    public List<BiographySkillDto> getBiographySkills() {
        return biographySkills;
    }

    public void setBiographySkills(List<BiographySkillDto> biographySkills) {
        this.biographySkills = biographySkills;
    }

    public List<PatentDto> getPatents() {
        return patents;
    }

    public void setPatents(List<PatentDto> patents) {
        this.patents = patents;
    }

    public List<AwardDto> getAwards() {
        return awards;
    }

    public void setAwards(List<AwardDto> awards) {
        this.awards = awards;
    }

    public List<PublicationDto> getPublications() {
        return publications;
    }

    public void setPublications(List<PublicationDto> publications) {
        this.publications = publications;
    }
    
    
    // Utility methods
    public void addBiographySkilledRole(BiographySkilledRoleDto dto) {
        if (biographySkilledRoles == null) {
            biographySkilledRoles = new ArrayList<BiographySkilledRoleDto>();
        }

        boolean isNew = true;
        for (BiographySkilledRoleDto aDto : biographySkilledRoles) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            biographySkilledRoles.add(dto);
        }
    }
    
    public void addBiographyDepartment(BiographyDepartmentDto dto) {
        if (biographyDepartments == null) {
            biographyDepartments = new ArrayList<BiographyDepartmentDto>();
        }

        boolean isNew = true;
        for (BiographyDepartmentDto aDto : biographyDepartments) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            biographyDepartments.add(dto);
        }
    }
    
    public void addBiographyCity(BiographyCityDto dto) {
        if (biographyCities == null) {
            biographyCities = new ArrayList<BiographyCityDto>();
        }

        boolean isNew = true;
        for (BiographyCityDto aDto : biographyCities) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            biographyCities.add(dto);
        }
    }
    
    public void addBiographySkill(BiographySkillDto dto) {
        if (biographySkills == null) {
            biographySkills = new ArrayList<BiographySkillDto>();
        }

        boolean isNew = true;
        for (BiographySkillDto aDto : biographySkills) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            biographySkills.add(dto);
        }
    }
    
    public void addPatent(PatentDto dto) {
        if (patents == null) {
            patents = new ArrayList<PatentDto>();
        }

        boolean isNew = true;
        for (PatentDto aDto : patents) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            patents.add(dto);
        }
    }
    
    public void addAward(AwardDto dto) {
        if (awards == null) {
            awards = new ArrayList<AwardDto>();
        }

        boolean isNew = true;
        for (AwardDto aDto : awards) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            awards.add(dto);
        }
    }
    
    public void addPublication(PublicationDto dto) {
        if (publications == null) {
            publications = new ArrayList<PublicationDto>();
        }

        boolean isNew = true;
        for (PublicationDto aDto : publications) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            publications.add(dto);
        }
    }
}
