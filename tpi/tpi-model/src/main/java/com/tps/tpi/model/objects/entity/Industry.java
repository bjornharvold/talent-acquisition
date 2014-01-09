package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.search.annotations.Indexed;

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
public class Industry extends AbstractReferenceDataEntity implements Serializable {
    private final static long serialVersionUID = 52L;

    private IndustryGroup industryGroup;

    @ManyToOne(optional = false, targetEntity = IndustryGroup.class)
    public IndustryGroup getIndustryGroup() {
        return industryGroup;
    }

    public void setIndustryGroup(IndustryGroup industryGroup) {
        this.industryGroup = industryGroup;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Industry");
        sb.append(super.toString());
        sb.append("{industryGroup=").append(industryGroup);
        sb.append('}');
        return sb.toString();
    }
}