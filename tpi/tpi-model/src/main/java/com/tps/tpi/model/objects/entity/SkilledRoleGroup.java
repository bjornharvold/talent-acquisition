package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.*;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 9, 2009
 * Time: 1:51:02 PM
 * Responsibility:
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
public class SkilledRoleGroup extends AbstractReferenceDataEntity implements Serializable {
    private final static long serialVersionUID = 7L;
    private SkilledRoleGroup parent;

    @ManyToOne(optional = true, targetEntity = SkilledRoleGroup.class)
    public SkilledRoleGroup getParent() {
        return parent;
    }

    public void setParent(SkilledRoleGroup parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SkilledRoleGroup");
        sb.append(super.toString());
        sb.append("{parent=").append(parent);
        sb.append('}');
        return sb.toString();
    }
}