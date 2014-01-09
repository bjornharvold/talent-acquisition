package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 3:45:07 PM
 * Responsibility:
 */
public class BiographySkilledRoleDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1008L;
    private String biography;
    private SkilledRoleDto skilledRole;
    private Integer years;

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public SkilledRoleDto getSkilledRole() {
        return skilledRole;
    }

    public void setSkilledRole(SkilledRoleDto skilledRole) {
        this.skilledRole = skilledRole;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }
}
