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
 * Date: Oct 8, 2009
 * Time: 4:30:21 PM
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
public class UserProxy extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 44L;
    private User owner;
    private User proxy;

    @ManyToOne(optional = false, targetEntity = User.class)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToOne(optional = false, targetEntity = User.class)
    public User getProxy() {
        return proxy;
    }

    public void setProxy(User proxy) {
        this.proxy = proxy;
    }
}
