package com.tps.tpi.model.objects.dto;

import com.tps.tpi.model.objects.lite.PersonLite;

import java.util.Date;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 11:16:18 AM
 * Responsibility:
 */
public class EndorsementDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1020L;
    private Integer stars;
    private Date endorsedOn;
    private PersonLite endorser;

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Date getEndorsedOn() {
        return endorsedOn;
    }

    public void setEndorsedOn(Date endorsedOn) {
        this.endorsedOn = endorsedOn;
    }

    public PersonLite getEndorser() {
        return endorser;
    }

    public void setEndorser(PersonLite endorser) {
        this.endorser = endorser;
    }
}
