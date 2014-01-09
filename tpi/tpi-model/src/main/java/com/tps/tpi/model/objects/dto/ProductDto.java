package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 26, 2009
 * Time: 3:26:56 PM
 * Responsibility:
 */
public class ProductDto extends AbstractReferenceDataDto implements Serializable {
    private final static long serialVersionUID = 1031L;
    private String company;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
