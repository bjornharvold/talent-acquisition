package com.tps.tpi.model.objects.dto;

import java.util.Date;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 3:47:30 PM
 * Responsibility:
 */
public class AwardDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1003L;
    private String biography;
    private String awardName;
    private Date issuedDate;
    private String issuer;

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
