package com.tps.tpi.model.objects.dto;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 12:36:42 PM
 * Responsibility:
 */
public class ProjectDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1032L;
    private String history;
    private String name;
    private CompanyDto company;
    private SkilledRoleDto skilledRole;
    private CityDto city;
    private Date from;
    private Date to;
    private List<ProjectEndorsementDto> projectEndorsements;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public SkilledRoleDto getSkilledRole() {
        return skilledRole;
    }

    public void setSkilledRole(SkilledRoleDto skilledRole) {
        this.skilledRole = skilledRole;
    }

    public CityDto getCity() {
        return city;
    }

    public void setCity(CityDto city) {
        this.city = city;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public List<ProjectEndorsementDto> getProjectEndorsements() {
        return projectEndorsements;
    }

    public void setProjectEndorsements(List<ProjectEndorsementDto> projectEndorsements) {
        this.projectEndorsements = projectEndorsements;
    }

    public void addProjectEndorsement(ProjectEndorsementDto dto) {
        if (projectEndorsements == null) {
            projectEndorsements = new ArrayList<ProjectEndorsementDto>();
        }

        boolean isNew = true;
        for (ProjectEndorsementDto aDto : projectEndorsements) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            projectEndorsements.add(dto);
        }
    }
}
