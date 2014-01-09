package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import com.tps.tpi.model.objects.dto.SearchComponentGroupDto;
import com.tps.tpi.model.objects.enums.SearchTypeCd;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Sep 28, 2009
 * Time: 9:53:32 AM
 * Responsibility: An entity to persist all values from an advanced search
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
                ),
                @TypeDef(name = "type",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.SearchTypeCd")}
                )
        }
)
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class Search extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 56L;
    private User user;
    private String name;
    private String luceneQuery;
    private Integer hitCount;
    private List<Person> persons;
    private List<Object[]> personIdsWithRelevancy;
    private List<Object[]> rawSearchResults;
    private List<SearchComponentGroup> groups;

    @ManyToOne(optional = false, targetEntity = User.class)
    @JoinColumn
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLuceneQuery() {
        return luceneQuery;
    }

    public void setLuceneQuery(String luceneQuery) {
        this.luceneQuery = luceneQuery;
    }

    public Integer getHitCount() {
        return hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    @OneToMany(targetEntity = SearchComponentGroup.class, cascade = CascadeType.ALL)
    public List<SearchComponentGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<SearchComponentGroup> groups) {
        this.groups = groups;
    }

    @Transient
    public List<Object[]> getPersonIdsWithRelevancy() {
        return personIdsWithRelevancy;
    }

    public void setPersonIdsWithRelevancy(List<Object[]> personIdsWithRelevancy) {
        this.personIdsWithRelevancy = personIdsWithRelevancy;
    }

    @Transient
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Transient
    public List<Object[]> getRawSearchResults() {
        return rawSearchResults;
    }

    public void setRawSearchResults(List<Object[]> rawSearchResults) {
        this.rawSearchResults = rawSearchResults;
    }

    public void addGroup(SearchComponentGroup group) {
        if (groups == null) {
            groups = new ArrayList<SearchComponentGroup>();
        }

        boolean isNew = true;
        for (SearchComponentGroup entity : groups) {
            if (StringUtils.equals(group.getId(), entity.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            groups.add(group);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Search {");
        sb.append(super.toString());
        sb.append(", name='").append(name).append('\'');
        sb.append(", luceneQuery='").append(luceneQuery).append('\'');
        sb.append(", hitCount=").append(hitCount);
        sb.append('}');
        return sb.toString();
    }
}