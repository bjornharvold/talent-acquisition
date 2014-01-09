package com.tps.tpi.model.objects.dto;

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
public class SearchComponentGroupDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1045L;
    private String search;
    private String type;
    private List<SearchComponentDto> components;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SearchComponentDto> getComponents() {
        return components;
    }

    public void setComponents(List<SearchComponentDto> components) {
        this.components = components;
    }

    public void addComponent(SearchComponentDto componentDto) {
        if (components == null) {
            components = new ArrayList<SearchComponentDto>();
        }

        boolean isNew = true;
        for (SearchComponentDto dto : components) {
            if (StringUtils.equals(componentDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            components.add(componentDto);
        }
    }
}