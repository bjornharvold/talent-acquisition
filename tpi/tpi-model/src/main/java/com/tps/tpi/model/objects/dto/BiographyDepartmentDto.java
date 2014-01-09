package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 3:45:50 PM
 * Responsibility:
 */
public class BiographyDepartmentDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1005L;
    private String biography;
    private DepartmentDto department;
    private Integer years;

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }
}
