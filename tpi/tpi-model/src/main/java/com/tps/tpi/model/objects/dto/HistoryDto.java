package com.tps.tpi.model.objects.dto;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 12:34:13 PM
 * Responsibility:
 */
public class HistoryDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1021L;
    private String person;
    private List<PositionDto> positions;
    private List<ProjectDto> projects;
    private List<CoverageDto> coverages;
    private List<AffiliationDto> affiliations;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public List<PositionDto> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionDto> positions) {
        this.positions = positions;
    }

    public List<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDto> projects) {
        this.projects = projects;
    }

    public List<CoverageDto> getCoverages() {
        return coverages;
    }

    public void setCoverages(List<CoverageDto> coverages) {
        this.coverages = coverages;
    }

    public List<AffiliationDto> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(List<AffiliationDto> affiliations) {
        this.affiliations = affiliations;
    }

    public void addAffiliation(AffiliationDto dto) {
        if (affiliations == null) {
            affiliations = new ArrayList<AffiliationDto>();
        }

        boolean isNew = true;
        for (AffiliationDto aDto : affiliations) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            affiliations.add(dto);
        }
    }

    public void addCoverage(CoverageDto dto) {
        if (coverages == null) {
            coverages = new ArrayList<CoverageDto>();
        }

        boolean isNew = true;
        for (CoverageDto aDto : coverages) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            coverages.add(dto);
        }
    }

    public void addPosition(PositionDto dto) {
        if (positions == null) {
            positions = new ArrayList<PositionDto>();
        }

        boolean isNew = true;
        for (PositionDto aDto : positions) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            positions.add(dto);
        }
        
    }

    public void addProject(ProjectDto dto) {
        if (projects == null) {
            projects = new ArrayList<ProjectDto>();
        }

        boolean isNew = true;
        for (ProjectDto aDto : projects) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            projects.add(dto);
        }
    }
}
