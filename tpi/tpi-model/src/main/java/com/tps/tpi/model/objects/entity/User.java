/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold.
 */

package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import com.tps.tpi.model.objects.enums.UserStatusCd;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.NotNull;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A user who can log into the system. There is a one-to-one user-person relationship
 */
@Entity
@TypeDefs(
        {
                @TypeDef(name = "status",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.UserStatusCd")}
                ),
                @TypeDef(name = "country",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.CountryCd")}
                ),
                @TypeDef(
                        name = "encryptedString",
                        typeClass = EncryptedStringType.class,
                        parameters = {
                                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
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
public class User extends AbstractEntity implements Serializable, UserDetails {
    private final static long serialVersionUID = 31L;

    private List<UserRole> roles = new ArrayList<UserRole>();
    private String username;
    private String email;
    private UserStatusCd status;
    private String password;
    private String passwordConfirm;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    @NotNull
    @Column(nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    @Type(type = "status")
    public UserStatusCd getStatus() {
        return status;
    }

    public void setStatus(UserStatusCd status) {
        this.status = status;
    }

    @NotNull
    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    // spring security
    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return this.getStatus() != UserStatusCd.LOCKED;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return this.getStatus() != UserStatusCd.LOCKED;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return this.getStatus() == UserStatusCd.ACTIVE;
    }

    @Override
    @Transient
    public GrantedAuthority[] getAuthorities() {
        GrantedAuthority[] result = null;

        if (roles != null) {
            result = new GrantedAuthority[roles.size()];

            for (int i = 0; i < result.length; i++) {
                UserRole ur = roles.get(i);
                result[i] = new GrantedAuthorityImpl(ur.getRole().getCode());
            }
        }

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("User");
        sb.append(super.toString());
        sb.append(", username='").append(username).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", status=").append(status);
        sb.append(", password='").append(password).append('\'');
        sb.append(", passwordConfirm='").append(passwordConfirm).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
