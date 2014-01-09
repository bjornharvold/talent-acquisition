package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.validator.Range;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Sep 28, 2009
 * Time: 12:36:14 PM
 * Responsibility: A title is a standardized title such as CEO. A company title is their definition and weight of a standard title
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
public class CompanyTitle extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 30L;

    private Integer rating;
    private Company company;
    private Title title;

    @Range(min = 0, max = 100)
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @ManyToOne(optional = false, targetEntity = Company.class)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToOne(optional = false, targetEntity = Title.class)
    @IndexedEmbedded
    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CompanyTitle {");
        sb.append(super.toString());
        sb.append(", rating=").append(rating);
        sb.append(", company=").append(company);
        sb.append(", title=").append(title);
        sb.append('}');
        return sb.toString();
    }
}
