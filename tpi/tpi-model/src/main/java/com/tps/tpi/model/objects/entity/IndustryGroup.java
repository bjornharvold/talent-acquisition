package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 15, 2009
 * Time: 12:33:00 PM
 * Responsibility:
 */
@Entity
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
public class IndustryGroup extends AbstractReferenceDataEntity implements Serializable {
    private final static long serialVersionUID = 51L;

    private IndustryGroup parent;

    @ManyToOne(optional = true, targetEntity = IndustryGroup.class)
    public IndustryGroup getParent() {
        return parent;
    }

    public void setParent(IndustryGroup parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("IndustryGroup");
        sb.append(super.toString());
        sb.append("{parent=").append(parent);
        sb.append('}');
        return sb.toString();
    }
}
