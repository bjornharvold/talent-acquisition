package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 3:47:18 PM
 * Responsibility:
 */
public class PatentDto extends AbstractReferenceDataDto implements Serializable {
    private final static long serialVersionUID = 1025L;
    private String biography;

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
