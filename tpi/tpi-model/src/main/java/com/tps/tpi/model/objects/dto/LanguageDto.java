package com.tps.tpi.model.objects.dto;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 4:00:11 PM
 * Responsibility:
 */
public class LanguageDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1023L;
    private String education;
    private String type;
    private String verbal;
    private String readwrite;
    private List<LanguageEndorsementDto> languageEndorsements;

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVerbal() {
        return verbal;
    }

    public void setVerbal(String verbal) {
        this.verbal = verbal;
    }

    public String getReadwrite() {
        return readwrite;
    }

    public void setReadwrite(String readwrite) {
        this.readwrite = readwrite;
    }

    public List<LanguageEndorsementDto> getLanguageEndorsements() {
        return languageEndorsements;
    }

    public void setLanguageEndorsements(List<LanguageEndorsementDto> languageEndorsements) {
        this.languageEndorsements = languageEndorsements;
    }

    public void addLanguageEndorsement(LanguageEndorsementDto dto) {
        if (languageEndorsements == null) {
            languageEndorsements = new ArrayList<LanguageEndorsementDto>();
        }

        boolean isNew = true;
        for (LanguageEndorsementDto aDto : languageEndorsements) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            languageEndorsements.add(dto);
        }
    }
}
