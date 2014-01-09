package com.tps.tpi.model.objects.dto;

import java.util.Date;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 3:48:04 PM
 * Responsibility:
 */
public class PublicationDto extends AbstractReferenceDataDto implements Serializable {
    private final static long serialVersionUID = 1034L;
    private String type;
    private Date issueDate;
    private String biography;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
