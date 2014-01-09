package com.tps.tpi.model.objects.dto;

import com.tps.tpi.model.objects.lite.PersonLite;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:59:48 AM
 * Responsibility:
 */
public class SearchDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1044L;
    private String company;
    private String user;
    private String name;
    private Integer hitCount;
    private List<PersonLite> persons;
    private List<SearchComponentGroupDto> groups;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHitCount() {
        return hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    public List<PersonLite> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonLite> persons) {
        this.persons = persons;
    }

    public List<SearchComponentGroupDto> getGroups() {
        return groups;
    }

    public void setGroups(List<SearchComponentGroupDto> groups) {
        this.groups = groups;
    }

    
    public void addGroup(SearchComponentGroupDto gDto) {
        if (groups == null) {
            groups = new ArrayList<SearchComponentGroupDto>();
        }

        boolean isNew = true;
        for (SearchComponentGroupDto dto : groups) {
            if (StringUtils.equals(gDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            groups.add(gDto);
        }
    }
}