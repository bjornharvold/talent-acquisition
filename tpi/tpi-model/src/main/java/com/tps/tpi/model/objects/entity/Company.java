package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.exception.ModelException;
import com.tps.tpi.model.hibernate.EnumUserType;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Sep 28, 2009
 * Time: 10:00:23 AM
 * Responsibility: A company is what a Person is associated with
 */
@Entity
@Indexed
@TypeDefs(
        {
                @TypeDef(name = "recordCreatorType",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.RecordCreatorTypeCd")}
                ),
                @TypeDef(name = "recordStatusType",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.RecordStatusTypeCd")}
                )
        }
)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company extends AbstractReferenceDataEntity implements Serializable {
    private final static long serialVersionUID = 6L;
    private String contactName;
    private String contactPhone;
    private Company parent;
    private List<CompanyName> companyNames;
    private List<CompanyIndustry> companyIndustries;

    // if the company is renamed we need to capture this by creating a new company but leaving a link to the old company
    private Company oldCompany;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = CompanyName.class)
    @IndexedEmbedded
    public List<CompanyName> getCompanyNames() {
        return companyNames;
    }

    public void setCompanyNames(List<CompanyName> companyNames) {
        this.companyNames = companyNames;
    }

    @ManyToOne(optional = true, targetEntity = Company.class)
    public Company getParent() {
        return parent;
    }

    public void setParent(Company parent) {
        this.parent = parent;
    }

    @ManyToOne(optional = true, targetEntity = Company.class)
    public Company getOldCompany() {
        return oldCompany;
    }

    public void setOldCompany(Company oldCompany) {
        this.oldCompany = oldCompany;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @OneToMany(targetEntity = CompanyIndustry.class)
    @IndexedEmbedded
    public List<CompanyIndustry> getCompanyIndustries() {
        return companyIndustries;
    }

    public void setCompanyIndustries(List<CompanyIndustry> companyIndustries) {
        this.companyIndustries = companyIndustries;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Company {");
        sb.append(super.toString());
        sb.append(", contactName='").append(contactName).append('\'');
        sb.append(", contactPhone='").append(contactPhone).append('\'');
        sb.append(", companyNames=").append(companyNames);
        sb.append('}');
        return sb.toString();
    }

    public void addCompanyIndustry(CompanyIndustry companyIndustry) throws ModelException {
        if (companyIndustries == null) {
            companyIndustries = new ArrayList<CompanyIndustry>();
        }

        if (StringUtils.isBlank(companyIndustry.getId())) {
            throw new ModelException("CompanyIndustry entity needs to already be persisted");
        }

        boolean isNew = true;

        for (CompanyIndustry bs : companyIndustries) {
            if (StringUtils.equals(companyIndustry.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            companyIndustries.add(companyIndustry);
        }
    }
}