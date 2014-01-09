package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.*;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 2, 2009
 * Time: 1:12:45 PM
 * Responsibility: A project this person has worked on in the past
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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Project extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 23L;
    private History history;
    private String name;
    private Company company;
    private SkilledRole skilledRole;
    private City city;
    private Date from;
    private Date to;
    private List<ProjectEndorsement> projectEndorsements;

    @ManyToOne(optional = false, targetEntity = History.class)
    @JoinColumn
    @ContainedIn
    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    @Field
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(optional = false, targetEntity = Company.class)
    @JoinColumn
    @IndexedEmbedded
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToOne(optional = false, targetEntity = SkilledRole.class)
    @JoinColumn
    @IndexedEmbedded
    public SkilledRole getSkilledRole() {
        return skilledRole;
    }

    public void setSkilledRole(SkilledRole companyTitle) {
        this.skilledRole = companyTitle;
    }

    @ManyToOne(optional = false, targetEntity = City.class)
    @JoinColumn
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    @OneToMany(mappedBy = "project")
    public List<ProjectEndorsement> getProjectEndorsements() {
        return projectEndorsements;
    }

    public void setProjectEndorsements(List<ProjectEndorsement> projectEndorsements) {
        this.projectEndorsements = projectEndorsements;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Project {");
        sb.append(super.toString());
        sb.append(", name='").append(name).append('\'');
        sb.append(", from=").append(from);
        sb.append(", to=").append(to);
        sb.append('}');
        return sb.toString();
    }
}
