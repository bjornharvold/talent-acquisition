/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold.
 */

package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.NotNull;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * User: bjorn
 * Date: Nov 13, 2008
 * Time: 1:36:28 AM
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
public class UserRole extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 32L;

    private Role role;
    private User user;

    public UserRole(){}

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    @NotNull
    @ManyToOne(optional = false, targetEntity = Role.class)
    @JoinColumn
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @NotNull
    @ManyToOne(optional = false, targetEntity = User.class)
    @JoinColumn
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
