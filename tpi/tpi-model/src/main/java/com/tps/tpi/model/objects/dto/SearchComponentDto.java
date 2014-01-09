package com.tps.tpi.model.objects.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:59:48 AM
 * Responsibility:
 */
public class SearchComponentDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1046L;
    private Map<String, String> searchMap;

    public Map<String, String> getSearchMap() {
        return searchMap;
    }

    public void setSearchMap(Map<String, String> searchMap) {
        this.searchMap = searchMap;
    }
}