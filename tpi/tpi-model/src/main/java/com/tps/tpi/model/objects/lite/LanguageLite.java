package com.tps.tpi.model.objects.lite;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Nov 10, 2009
 * Time: 1:38:23 AM
 * Responsibility:
 */
public class LanguageLite extends AbstractLite implements Serializable {
    private final static long serialVersionUID = 10002L;
    private String type;
    private String verbal;
    private String readwrite;
    private Integer endorsements;
    private Integer averageStars;

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
