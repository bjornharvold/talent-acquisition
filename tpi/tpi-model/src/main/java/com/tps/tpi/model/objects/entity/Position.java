package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import com.tps.tpi.model.objects.enums.EmploymentTypeCd;
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
 * Time: 1:00:10 PM
 * Responsibility: A position a person held at a company
 */
@Entity
@Indexed
@TypeDefs(
        {
                @TypeDef(name = "type",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.EmploymentTypeCd")}
                ),
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
public class Position extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 19L;
    private History history;
    private SkilledRole skilledRole;
    private Department department;
    private EmploymentTypeCd type;
    private City city;
    private Date from;
    private Date to;
    private List<PositionEndorsement> positionEndorsements;

    @ManyToOne(optional = false, targetEntity = History.class)
    @JoinColumn
    @ContainedIn
    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    @ManyToOne(optional = false, targetEntity = SkilledRole.class)
    @JoinColumn
    public SkilledRole getSkilledRole() {
        return skilledRole;
    }

    public void setSkilledRole(SkilledRole skilledRole) {
        this.skilledRole = skilledRole;
    }

    @ManyToOne(optional = false, targetEntity = Department.class)
    @JoinColumn
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @ManyToOne(optional = false, targetEntity = City.class)
    @JoinColumn
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Type(type = "type")
    @Field
    public EmploymentTypeCd getType() {
        return type;
    }

    public void setType(EmploymentTypeCd type) {
        this.type = type;
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

    @OneToMany(mappedBy = "position")
    public List<PositionEndorsement> getPositionEndorsements() {
        return positionEndorsements;
    }

    public void setPositionEndorsements(List<PositionEndorsement> positionEndorsements) {
        this.positionEndorsements = positionEndorsements;
    }
}
