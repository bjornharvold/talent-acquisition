package com.tps.tpi.model.objects.dto;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 12:36:26 PM
 * Responsibility:
 */
public class PositionDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1028L;
    private String history;
    private SkilledRoleDto skilledRole;
    private String department;
    private String departmentName;
    private String companyName;
    private String type;
    private String city;
    private String cityName;
    private Date from;
    private Date to;
    private List<PositionEndorsementDto> positionEndorsements;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public SkilledRoleDto getSkilledRole() {
        return skilledRole;
    }

    public void setSkilledRole(SkilledRoleDto skilledRole) {
        this.skilledRole = skilledRole;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public List<PositionEndorsementDto> getPositionEndorsements() {
        return positionEndorsements;
    }

    public void setPositionEndorsements(List<PositionEndorsementDto> positionEndorsements) {
        this.positionEndorsements = positionEndorsements;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void addPositionEndorsement(PositionEndorsementDto dto) {
        if (positionEndorsements == null) {
            positionEndorsements = new ArrayList<PositionEndorsementDto>();
        }

        boolean isNew = true;
        for (PositionEndorsementDto aDto : positionEndorsements) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            positionEndorsements.add(dto);
        }
    }
}
