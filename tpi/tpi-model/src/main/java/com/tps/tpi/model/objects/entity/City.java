package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.*;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Sep 28, 2009
 * Time: 10:00:59 AM
 * Responsibility: City reference data
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
public class City extends AbstractReferenceDataEntity implements Serializable {
    private final static long serialVersionUID = 5L;
    private Country country;

    @ManyToOne(optional = false, targetEntity = Country.class)
    @IndexedEmbedded
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("City");
        sb.append(super.toString());
        sb.append("{country=").append(country);
        sb.append('}');
        return sb.toString();
    }
}
