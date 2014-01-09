package com.tps.tpi.model.objects.dto;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 3:43:17 PM
 * Responsibility:
 */
public class EducationDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1019L;
    private String person;
    private List<DegreeDto> degrees;
    private List<EducationCertificationDto> educationCertifications;
    private List<LanguageDto> languages;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public List<DegreeDto> getDegrees() {
        return degrees;
    }

    public void setDegrees(List<DegreeDto> degrees) {
        this.degrees = degrees;
    }

    public List<EducationCertificationDto> getEducationCertifications() {
        return educationCertifications;
    }

    public void setEducationCertifications(List<EducationCertificationDto> educationCertifications) {
        this.educationCertifications = educationCertifications;
    }

    public List<LanguageDto> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguageDto> languages) {
        this.languages = languages;
    }

    public void addDegree(DegreeDto dto) {
        if (degrees == null) {
            degrees = new ArrayList<DegreeDto>();
        }

        boolean isNew = true;
        for (DegreeDto aDto : degrees) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            degrees.add(dto);
        }
    }

    public void addEducationCertification(EducationCertificationDto dto) {
        if (educationCertifications == null) {
            educationCertifications = new ArrayList<EducationCertificationDto>();
        }

        boolean isNew = true;
        for (EducationCertificationDto aDto : educationCertifications) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            educationCertifications.add(dto);
        }
    }

    public void addLanguage(LanguageDto dto) {
        if (languages == null) {
            languages = new ArrayList<LanguageDto>();
        }

        boolean isNew = true;
        for (LanguageDto aDto : languages) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            languages.add(dto);
        }
    }
}
