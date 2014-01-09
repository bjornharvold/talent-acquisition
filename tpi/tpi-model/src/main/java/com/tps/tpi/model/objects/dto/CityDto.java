package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 26, 2009
 * Time: 3:23:46 PM
 * Responsibility:
 */
public class CityDto extends AbstractReferenceDataDto implements Serializable {
    private final static long serialVersionUID = 1011L;
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
