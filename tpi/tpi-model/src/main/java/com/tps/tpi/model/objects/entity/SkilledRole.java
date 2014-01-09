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
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 8, 2009
 * Time: 10:05:53 AM
 * Responsibility: A professional role is different than a Title. A role can be 'Janitor' all derivations of while Title
 * is recognized global high-level titles such as CEO, CTO or COO
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
public class SkilledRole extends AbstractReferenceDataEntity implements Serializable {
    private final static long serialVersionUID = 41L;
    private SkilledRoleGroup skilledRoleGroup;
    List<BiographySkilledRole> biographySkilledRoles;

    @OneToMany(targetEntity = BiographySkilledRole.class, mappedBy = "skilledRole")
    @ContainedIn
    public List<BiographySkilledRole> getBiographySkilledRoles() {
        return biographySkilledRoles;
    }

    public void setBiographySkilledRoles(List<BiographySkilledRole> biographySkilledRoles) {
        this.biographySkilledRoles = biographySkilledRoles;
    }

    @ManyToOne(optional = false, targetEntity = SkilledRoleGroup.class)
    @IndexedEmbedded
    public SkilledRoleGroup getSkilledRoleGroup() {
        return skilledRoleGroup;
    }

    public void setSkilledRoleGroup(SkilledRoleGroup skilledRoleGroup) {
        this.skilledRoleGroup = skilledRoleGroup;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SkilledRole");
        sb.append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
