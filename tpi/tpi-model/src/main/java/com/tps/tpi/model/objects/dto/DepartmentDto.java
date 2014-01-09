package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 26, 2009
 * Time: 3:07:34 PM
 * Responsibility:
 */
public class DepartmentDto extends AbstractReferenceDataDto implements Serializable {
    private final static long serialVersionUID = 1017L;
    private String division;
    private String parent;

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
