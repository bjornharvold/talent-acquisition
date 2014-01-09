package com.tps.tpi.model.objects.entity;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Jan 2, 2010
 * Time: 2:11:04 AM
 * Responsibility:
 */
@Entity
@Indexed
public class CompanyIndustry extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 57L;
    private Company company;
    private Industry industry;

    @ManyToOne(optional = false, targetEntity = Company.class)
    @ContainedIn
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToOne(optional = false, targetEntity = Industry.class)
    @IndexedEmbedded
    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }
}
