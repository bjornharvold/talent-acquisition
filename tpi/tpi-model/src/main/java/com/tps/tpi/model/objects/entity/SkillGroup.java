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
 * Date: Oct 9, 2009
 * Time: 1:51:02 PM
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
public class SkillGroup extends AbstractReferenceDataEntity implements Serializable {
    private final static long serialVersionUID = 46L;
    private SkillGroup parent;

    @ManyToOne(optional = true, targetEntity = SkillGroup.class)
    public SkillGroup getParent() {
        return parent;
    }

    public void setParent(SkillGroup parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SkillGroup");
        sb.append(super.toString());
        sb.append("{parent=").append(parent);
        sb.append('}');
        return sb.toString();
    }
}
