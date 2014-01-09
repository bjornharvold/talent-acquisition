package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.*;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

/**
 * User: Bjorn Harvold
 * Date: Sep 28, 2009
 * Time: 9:10:10 AM
 * Responsibility: Department within a company
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
public class Department extends AbstractReferenceDataEntity implements Serializable {
    private final static long serialVersionUID = 10L;
    private Division division;
    private Department parent;
    private Set<BiographyDepartment> biographyDepartments;

    @OneToMany(targetEntity = BiographyDepartment.class, mappedBy = "department")
    @ContainedIn
    public Set<BiographyDepartment> getBiographyDepartments() {
        return biographyDepartments;
    }

    public void setBiographyDepartments(Set<BiographyDepartment> biographyDepartments) {
        this.biographyDepartments = biographyDepartments;
    }

    @ManyToOne(optional = false, targetEntity = Division.class)
    @IndexedEmbedded
    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    @ManyToOne(optional = true, targetEntity = Department.class)
    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Department");
        sb.append(super.toString());
        sb.append("{division=").append(division);
        sb.append(", parent=").append(parent);
        sb.append('}');
        return sb.toString();
    }
}
